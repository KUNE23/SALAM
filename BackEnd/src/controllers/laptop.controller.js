const service = require("../services/laptop.service");
const { success } = require("../utils/response");

async function list(req, res, next) {
  try {
    const result = await service.list(req.validated.query);
    success(res, "Berhasil mengambil data", result.data, 200, result.meta);
  } catch (err) {
    next(err);
  }
}

async function available(req, res, next) {
  try {
    success(res, "Berhasil mengambil laptop tersedia", await service.available());
  } catch (err) {
    next(err);
  }
}

async function detail(req, res, next) {
  try {
    success(res, "Berhasil mengambil detail laptop", await service.detail(req.validated.params.id));
  } catch (err) {
    next(err);
  }
}

async function create(req, res, next) {
  try {
    success(res, "Laptop berhasil dibuat", await service.create(req.validated.body, req.file), 201);
  } catch (err) {
    next(err);
  }
}

async function update(req, res, next) {
  try {
    success(res, "Laptop berhasil diperbarui", await service.update(req.validated.params.id, req.validated.body, req.file));
  } catch (err) {
    next(err);
  }
}

async function remove(req, res, next) {
  try {
    await service.remove(req.validated.params.id);
    success(res, "Laptop berhasil dihapus");
  } catch (err) {
    next(err);
  }
}

async function changeStatus(req, res, next) {
  try {
    success(res, "Status laptop berhasil diperbarui", await service.changeStatus(req.validated.params.id, req.validated.body.status));
  } catch (err) {
    next(err);
  }
}

module.exports = { list, available, detail, create, update, remove, changeStatus };
