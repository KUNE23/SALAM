const argon2 = require("argon2");
const prisma = require("../config/prisma");
const { AppError } = require("../utils/response");
const { signAccessToken, signRefreshToken, verifyRefreshToken } = require("../utils/token");

const userSelect = { id: true, name: true, email: true, role: true, status: true, avatarUrl: true, nisn: true, className: true, major: true, employeeNumber: true };

async function login(email, password) {
  const user = await prisma.user.findUnique({ where: { email } });
  if (!user || user.status !== "ACTIVE") throw new AppError("Email atau password salah", 401);
  const valid = await argon2.verify(user.password, password);
  if (!valid) throw new AppError("Email atau password salah", 401);
  const safeUser = await prisma.user.findUnique({ where: { id: user.id }, select: userSelect });
  const accessToken = signAccessToken(user);
  const refreshToken = signRefreshToken(user);
  await prisma.user.update({ where: { id: user.id }, data: { refreshTokenHash: await argon2.hash(refreshToken) } });
  return { accessToken, refreshToken, user: safeUser };
}

async function register(body) {
  const password = await argon2.hash(body.password);
  let user;
  try {
    user = await prisma.user.create({
      data: { name: body.name, email: body.email, password, role: "SISWA" },
      select: userSelect
    });
  } catch {
    throw new AppError("Email sudah digunakan", 409);
  }
  const accessToken = signAccessToken(user);
  const refreshToken = signRefreshToken(user);
  await prisma.user.update({ where: { id: user.id }, data: { refreshTokenHash: await argon2.hash(refreshToken) } });
  return { accessToken, refreshToken, user };
}

async function refresh(refreshToken) {
  let payload;
  try {
    payload = verifyRefreshToken(refreshToken);
  } catch {
    throw new AppError("Refresh token tidak valid", 401);
  }
  const user = await prisma.user.findUnique({ where: { id: payload.sub } });
  if (!user || !user.refreshTokenHash || user.status !== "ACTIVE") throw new AppError("Refresh token tidak valid", 401);
  const valid = await argon2.verify(user.refreshTokenHash, refreshToken);
  if (!valid) throw new AppError("Refresh token tidak valid", 401);
  const nextRefreshToken = signRefreshToken(user);
  await prisma.user.update({ where: { id: user.id }, data: { refreshTokenHash: await argon2.hash(nextRefreshToken) } });
  return { accessToken: signAccessToken(user), refreshToken: nextRefreshToken };
}

async function logout(userId) {
  await prisma.user.update({ where: { id: userId }, data: { refreshTokenHash: null } });
}

module.exports = { login, register, refresh, logout, userSelect };
