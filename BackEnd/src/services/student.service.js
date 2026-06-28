const argon2 = require("argon2");
const prisma = require("../config/prisma");
const { getPagination, meta } = require("../utils/pagination");
const { AppError } = require("../utils/response");
const { userSelect } = require("./auth.service");

function where(query) {
  const filters = { role: "SISWA" };
  if (query.status) filters.status = query.status;
  if (query.className) filters.className = query.className;
  if (query.search) {
    filters.OR = [
      { name: { contains: query.search, mode: "insensitive" } },
      { email: { contains: query.search, mode: "insensitive" } },
      { nisn: { contains: query.search, mode: "insensitive" } }
    ];
  }
  return filters;
}

async function list(query) {
  const { page, limit, skip } = getPagination(query);
  const filters = where(query);
  const [data, total] = await Promise.all([
    prisma.user.findMany({ where: filters, select: userSelect, skip, take: limit, orderBy: { createdAt: "desc" } }),
    prisma.user.count({ where: filters })
  ]);
  return { data, meta: meta(page, limit, total) };
}

async function create(body) {
  const password = await argon2.hash(body.password || body.nisn);
  try {
    return await prisma.user.create({ data: { name: body.name, email: body.email, password, role: "SISWA", nisn: body.nisn, className: body.className, major: body.major, status: body.status || "ACTIVE" }, select: userSelect });
  } catch {
    throw new AppError("Email atau NISN sudah digunakan", 409);
  }
}

async function update(id, body) {
  const student = await prisma.user.findFirst({ where: { id, role: "SISWA" } });
  if (!student) throw new AppError("Siswa tidak ditemukan", 404);
  const data = {};
  for (const key of ["name", "email", "nisn", "className", "major", "status"]) if (body[key] !== undefined) data[key] = body[key];
  if (body.password) data.password = await argon2.hash(body.password);
  try {
    return await prisma.user.update({ where: { id }, data, select: userSelect });
  } catch {
    throw new AppError("Email atau NISN sudah digunakan", 409);
  }
}

async function remove(id) {
  const student = await prisma.user.findFirst({ where: { id, role: "SISWA" } });
  if (!student) throw new AppError("Siswa tidak ditemukan", 404);
  await prisma.user.delete({ where: { id } }).catch(() => { throw new AppError("Siswa tidak ditemukan", 404); });
}

async function changeStatus(id, status) {
  const student = await prisma.user.findFirst({ where: { id, role: "SISWA" } });
  if (!student) throw new AppError("Siswa tidak ditemukan", 404);
  return prisma.user.update({ where: { id }, data: { status }, select: userSelect }).catch(() => { throw new AppError("Siswa tidak ditemukan", 404); });
}

module.exports = { list, create, update, remove, changeStatus };
