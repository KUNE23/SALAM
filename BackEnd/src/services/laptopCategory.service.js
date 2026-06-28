const prisma = require("../config/prisma");
const { AppError } = require("../utils/response");

const defaults = [
  { name: "High Performance", description: "Workstation & Editing", unitCount: 24, status: "ACTIVE" },
  { name: "Programming", description: "MacBook & Linux Thin Client", unitCount: 18, status: "ACTIVE" },
  { name: "Standard Office", description: "Dokumen & Administrasi", unitCount: 42, status: "INACTIVE" }
];

function select() {
  return { id: true, name: true, description: true, unitCount: true, status: true, createdAt: true, updatedAt: true };
}

async function ensureDefaults() {
  const total = await prisma.laptopCategory.count();
  if (total > 0) return;
  await prisma.laptopCategory.createMany({ data: defaults, skipDuplicates: true });
}

async function list() {
  await ensureDefaults();
  return prisma.laptopCategory.findMany({ select: select(), orderBy: { createdAt: "asc" } });
}

async function create(body) {
  return prisma.laptopCategory.create({
    data: {
      name: body.name,
      description: body.description,
      unitCount: body.unitCount,
      status: body.status
    },
    select: select()
  }).catch(() => {
    throw new AppError("Nama kategori sudah digunakan", 409);
  });
}

async function update(id, body) {
  return prisma.laptopCategory.update({
    where: { id },
    data: body,
    select: select()
  }).catch(() => {
    throw new AppError("Kategori tidak ditemukan atau duplikat", 404);
  });
}

async function remove(id) {
  await prisma.laptopCategory.delete({ where: { id } }).catch(() => {
    throw new AppError("Kategori tidak ditemukan", 404);
  });
}

module.exports = { list, create, update, remove };
