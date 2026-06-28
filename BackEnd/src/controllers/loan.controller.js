const service = require("../services/loan.service");
const { success } = require("../utils/response");

async function list(req, res, next) {
  try {
    const result = await service.list(req.validated.query, req.user);
    success(res, "Berhasil mengambil data", result.data, 200, result.meta);
  } catch (err) {
    next(err);
  }
}

async function pending(req, res, next) {
  try {
    success(res, "Berhasil mengambil pengajuan", await service.pending());
  } catch (err) {
    next(err);
  }
}

async function mine(req, res, next) {
  try {
    const result = await service.list(req.validated?.query || {}, req.user);
    success(res, "Berhasil mengambil data", result.data, 200, result.meta);
  } catch (err) {
    next(err);
  }
}

async function create(req, res, next) {
  try {
    success(res, "Pengajuan berhasil dibuat", await service.create(req.user.id, req.validated.body.laptopCode), 201);
  } catch (err) {
    next(err);
  }
}

async function approve(req, res, next) {
  try {
    success(res, "Pengajuan berhasil disetujui", await service.approve(req.validated.params.id));
  } catch (err) {
    next(err);
  }
}

async function reject(req, res, next) {
  try {
    success(res, "Pengajuan berhasil ditolak", await service.reject(req.validated.params.id, req.validated.body.reason));
  } catch (err) {
    next(err);
  }
}

async function returnLoan(req, res, next) {
  try {
    success(res, "Laptop berhasil dikembalikan", await service.returnLoan(req.validated.params.id, req.user));
  } catch (err) {
    next(err);
  }
}

module.exports = { list, pending, mine, create, approve, reject, returnLoan };
