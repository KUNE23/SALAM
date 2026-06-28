const prisma = require("../config/prisma");
const { AppError } = require("../utils/response");

async function list(user) {
  const where = user.role === "SISWA" ? { userId: user.id } : { userId: null };
  return prisma.notification.findMany({ where, orderBy: { createdAt: "desc" } });
}

async function read(id, user) {
  const notification = await prisma.notification.findUnique({ where: { id } });
  if (!notification) throw new AppError("Notifikasi tidak ditemukan", 404);
  if (user.role === "SISWA" && notification.userId !== user.id) throw new AppError("Akses ditolak", 403);
  return prisma.notification.update({ where: { id }, data: { isRead: true } });
}

async function readAll(user) {
  const where = user.role === "SISWA" ? { userId: user.id } : { userId: null };
  await prisma.notification.updateMany({ where, data: { isRead: true } });
}

module.exports = { list, read, readAll };
