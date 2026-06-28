const service = require("../services/student.service");
const { success } = require("../utils/response");

async function list(req, res, next) {
  try {
    const result = await service.list(req.validated.query);
    success(res, "Berhasil mengambil data", result.data, 200, result.meta);
  } catch (err) {
    next(err);
  }
}

async function create(req, res, next) {
  try {
    success(res, "Siswa berhasil dibuat", await service.create(req.validated.body), 201);
  } catch (err) {
    next(err);
  }
}

async function update(req, res, next) {
  try {
    success(res, "Siswa berhasil diperbarui", await service.update(req.validated.params.id, req.validated.body));
  } catch (err) {
    next(err);
  }
}

async function remove(req, res, next) {
  try {
    await service.remove(req.validated.params.id);
    success(res, "Siswa berhasil dihapus");
  } catch (err) {
    next(err);
  }
}

async function changeStatus(req, res, next) {
  try {
    success(res, "Status siswa berhasil diperbarui", await service.changeStatus(req.validated.params.id, req.validated.body.status));
  } catch (err) {
    next(err);
  }
}

module.exports = { list, create, update, remove, changeStatus };
