const prisma = require("../config/prisma");
const { getPagination, meta } = require("../utils/pagination");
const { AppError } = require("../utils/response");
const { deadlineFromSetting, formatDate, formatTime, initials, loanDuration } = require("../utils/date");

const activeStatuses = ["PENDING", "APPROVED", "BORROWED"];

function mapLoan(loan) {
  return {
    id: loan.id,
    student: { name: loan.user.name, email: loan.user.email, initial: initials(loan.user.name) },
    laptop: { code: loan.laptop.code, name: `${loan.laptop.brand} ${loan.laptop.series}` },
    status: loan.status,
    requestDate: formatDate(loan.requestDate),
    startTime: formatTime(loan.startTime),
    returnTime: formatTime(loan.returnDeadline),
    duration: loanDuration(loan.startTime || loan.requestDate, loan.returnDeadline)
  };
}

const include = { user: { select: { name: true, email: true } }, laptop: { select: { brand: true, series: true, code: true } } };

async function list(query, user) {
  const { page, limit, skip } = getPagination(query);
  const where = {};
  if (query.status) where.status = query.status;
  if (user.role === "SISWA") where.userId = user.id;
  const [rows, total] = await Promise.all([
    prisma.loan.findMany({ where, include, skip, take: limit, orderBy: { createdAt: "desc" } }),
    prisma.loan.count({ where })
  ]);
  return { data: rows.map(mapLoan), meta: meta(page, limit, total) };
}

async function pending() {
  const rows = await prisma.loan.findMany({ where: { status: "PENDING" }, include, orderBy: { createdAt: "desc" } });
  return rows.map(mapLoan);
}

async function create(userId, laptopCode) {
  return prisma.$transaction(async (tx) => {
    const active = await tx.loan.findFirst({ where: { userId, status: { in: activeStatuses } } });
    if (active) throw new AppError("Kamu masih memiliki pengajuan atau pinjaman aktif", 409);
    const laptop = await tx.laptop.findUnique({ where: { code: laptopCode.toUpperCase() } });
    if (!laptop) throw new AppError("Laptop tidak ditemukan", 404);
    if (laptop.status !== "AVAILABLE") throw new AppError("Laptop tidak tersedia", 409);
    const setting = await tx.setting.findFirst();
    const now = new Date();
    const deadline = deadlineFromSetting(now, setting?.closeTime || "16:00", setting?.maxLoanDays || 7);
    await tx.laptop.update({ where: { id: laptop.id }, data: { status: "BORROWED" } });
    const loan = await tx.loan.create({
      data: { userId, laptopId: laptop.id, status: "BORROWED", startTime: now, approvedAt: now, returnDeadline: deadline },
      include
    });
    await tx.notification.create({ data: { userId, title: "Pinjaman berhasil", message: "Jangan lupa dikembalikan paling lambat jam 16.00.", type: "SUCCESS" } });
    return mapLoan(loan);
  });
}

async function approve(id) {
  return prisma.$transaction(async (tx) => {
    const loan = await tx.loan.findUnique({ where: { id }, include: { laptop: true, user: true } });
    if (!loan || loan.status !== "PENDING") throw new AppError("Pengajuan tidak valid", 409);
    if (loan.laptop.status !== "AVAILABLE") throw new AppError("Laptop tidak tersedia", 409);
    const setting = await tx.setting.findFirst();
    const now = new Date();
    const deadline = deadlineFromSetting(now, setting?.closeTime || "16:00", setting?.maxLoanDays || 7);
    await tx.laptop.update({ where: { id: loan.laptopId }, data: { status: "BORROWED" } });
    const updated = await tx.loan.update({ where: { id }, data: { status: "BORROWED", startTime: now, approvedAt: now, returnDeadline: deadline }, include });
    await tx.notification.create({ data: { userId: loan.userId, title: "Pengajuan disetujui", message: "Pengajuan peminjaman laptop kamu disetujui.", type: "SUCCESS" } });
    return mapLoan(updated);
  });
}

async function reject(id, reason) {
  return prisma.$transaction(async (tx) => {
    const loan = await tx.loan.findUnique({ where: { id }, include });
    if (!loan || loan.status !== "PENDING") throw new AppError("Pengajuan tidak valid", 409);
    const updated = await tx.loan.update({ where: { id }, data: { status: "REJECTED", rejectedAt: new Date(), rejectReason: reason || "Tidak disetujui" }, include });
    await tx.notification.create({ data: { userId: loan.userId, title: "Pengajuan ditolak", message: reason || "Pengajuan peminjaman laptop kamu ditolak.", type: "DANGER" } });
    return mapLoan(updated);
  });
}

async function returnLoan(id, user) {
  return prisma.$transaction(async (tx) => {
    const loan = await tx.loan.findUnique({ where: { id }, include });
    if (!loan || !["PENDING", "APPROVED", "BORROWED", "OVERDUE"].includes(loan.status)) throw new AppError("Pinjaman tidak valid", 409);
    if (user.role === "SISWA" && loan.userId !== user.id) throw new AppError("Akses ditolak", 403);
    await tx.laptop.update({ where: { id: loan.laptopId }, data: { status: "AVAILABLE" } });
    const updated = await tx.loan.update({ where: { id }, data: { status: "RETURNED", returnedAt: new Date() }, include });
    await tx.notification.create({ data: { userId: loan.userId, title: "Pengumpulan berhasil", message: "Terimakasih, semoga bermanfaat :)", type: "SUCCESS" } });
    return mapLoan(updated);
  });
}

module.exports = { list, pending, create, approve, reject, returnLoan };
