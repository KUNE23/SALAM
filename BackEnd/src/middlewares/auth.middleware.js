const prisma = require("../config/prisma");
const { verifyAccessToken } = require("../utils/token");
const { AppError } = require("../utils/response");

async function auth(req, res, next) {
  try {
    const header = req.headers.authorization || "";
    const [type, token] = header.split(" ");
    if (type !== "Bearer" || !token) throw new AppError("Token tidak tersedia", 401);
    const payload = verifyAccessToken(token);
    const user = await prisma.user.findUnique({
      where: { id: payload.sub },
      select: { id: true, name: true, email: true, role: true, status: true, nisn: true, className: true, major: true, avatarUrl: true, employeeNumber: true }
    });
    if (!user || user.status !== "ACTIVE") throw new AppError("Token tidak valid", 401);
    req.user = user;
    next();
  } catch (err) {
    next(err.statusCode ? err : new AppError("Token tidak valid", 401));
  }
}

module.exports = auth;
