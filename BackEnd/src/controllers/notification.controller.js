const service = require("../services/notification.service");
const { success } = require("../utils/response");

async function list(req, res, next) {
  try {
    success(res, "Berhasil mengambil notifikasi", await service.list(req.user));
  } catch (err) {
    next(err);
  }
}

async function read(req, res, next) {
  try {
    success(res, "Notifikasi sudah dibaca", await service.read(req.params.id, req.user));
  } catch (err) {
    next(err);
  }
}

async function readAll(req, res, next) {
  try {
    await service.readAll(req.user);
    success(res, "Semua notifikasi sudah dibaca");
  } catch (err) {
    next(err);
  }
}

module.exports = { list, read, readAll };
