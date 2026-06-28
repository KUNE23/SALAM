const argon2 = require("argon2");
const prisma = require("../config/prisma");
const { AppError } = require("../utils/response");
const { userSelect } = require("./auth.service");

async function get(id) {
  return prisma.user.findUnique({ where: { id }, select: userSelect });
}

async function update(id, body) {
  const data = {};
  for (const key of ["name", "avatarUrl", "nisn", "className", "major", "employeeNumber"]) if (body[key] !== undefined) data[key] = body[key];
  return prisma.user.update({ where: { id }, data, select: userSelect });
}

async function changePassword(id, currentPassword, newPassword) {
  const user = await prisma.user.findUnique({ where: { id } });
  const valid = user && await argon2.verify(user.password, currentPassword);
  if (!valid) throw new AppError("Password lama salah", 422);
  await prisma.user.update({ where: { id }, data: { password: await argon2.hash(newPassword), refreshTokenHash: null } });
}

module.exports = { get, update, changePassword };
