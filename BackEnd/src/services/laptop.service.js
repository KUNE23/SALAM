const prisma = require("../config/prisma");
const { getPagination, meta } = require("../utils/pagination");
const { AppError } = require("../utils/response");

function select() {
  return { id: true, brand: true, series: true, code: true, ram: true, storage: true, completeness: true, photoUrl: true, status: true, createdAt: true, updatedAt: true };
}

function where(query) {
  const filters = {};
  if (query.status) filters.status = query.status;
  if (query.search) {
    filters.OR = [
      { brand: { contains: query.search, mode: "insensitive" } },
      { series: { contains: query.search, mode: "insensitive" } },
      { code: { contains: query.search, mode: "insensitive" } }
    ];
  }
  return filters;
}

async function list(query) {
  const { page, limit, skip } = getPagination(query);
  const filters = where(query);
  const [data, total] = await Promise.all([
    prisma.laptop.findMany({ where: filters, select: select(), skip, take: limit, orderBy: { code: "asc" } }),
    prisma.laptop.count({ where: filters })
  ]);
  return { data, meta: meta(page, limit, total) };
}

async function available() {
  return prisma.laptop.findMany({ where: { status: "AVAILABLE" }, select: select(), orderBy: { code: "asc" } });
}

async function detail(id) {
  const laptop = await prisma.laptop.findUnique({ where: { id }, select: select() });
  if (!laptop) throw new AppError("Laptop tidak ditemukan", 404);
  return laptop;
}

async function create(body, file) {
  return prisma.laptop.create({ data: { brand: body.brand, series: body.series, code: body.code.toUpperCase(), ram: body.ram, storage: body.storage, completeness: body.completeness, photoUrl: file ? `/uploads/${file.filename}` : undefined }, select: select() }).catch(() => { throw new AppError("Kode laptop sudah digunakan", 409); });
}

async function update(id, body, file) {
  const data = {};
  for (const key of ["brand", "series", "ram", "storage", "completeness"]) if (body[key] !== undefined) data[key] = body[key];
  if (body.code) data.code = body.code.toUpperCase();
  if (file) data.photoUrl = `/uploads/${file.filename}`;
  return prisma.laptop.update({ where: { id }, data, select: select() }).catch(() => { throw new AppError("Laptop tidak ditemukan atau kode duplikat", 404); });
}

async function remove(id) {
  const laptop = await prisma.laptop.findUnique({ where: { id } });
  if (!laptop) throw new AppError("Laptop tidak ditemukan", 404);
  if (laptop.status === "BORROWED") throw new AppError("Laptop yang sedang dipinjam tidak boleh dihapus", 409);
  await prisma.laptop.delete({ where: { id } });
}

async function changeStatus(id, status) {
  return prisma.laptop.update({ where: { id }, data: { status }, select: select() }).catch(() => { throw new AppError("Laptop tidak ditemukan", 404); });
}

module.exports = { list, available, detail, create, update, remove, changeStatus };
