const service = require("../services/auth.service");
const { success } = require("../utils/response");

async function login(req, res, next) {
  try {
    const data = await service.login(req.validated.body.email, req.validated.body.password);
    success(res, "Login berhasil", data);
  } catch (err) {
    next(err);
  }
}

async function refresh(req, res, next) {
  try {
    const data = await service.refresh(req.validated.body.refreshToken);
    success(res, "Token berhasil diperbarui", data);
  } catch (err) {
    next(err);
  }
}

async function register(req, res, next) {
  try {
    const data = await service.register(req.validated.body);
    success(res, "Registrasi berhasil", data, 201);
  } catch (err) {
    next(err);
  }
}

async function logout(req, res, next) {
  try {
    await service.logout(req.user.id);
    success(res, "Logout berhasil");
  } catch (err) {
    next(err);
  }
}

async function me(req, res) {
  success(res, "Berhasil mengambil data", req.user);
}

module.exports = { login, register, refresh, logout, me };
