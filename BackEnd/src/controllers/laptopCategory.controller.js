const service = require("../services/laptopCategory.service");
const { success } = require("../utils/response");

async function list(req, res, next) {
  try {
    success(res, "Berhasil mengambil kategori laptop", await service.list());
  } catch (err) {
    next(err);
  }
}

async function create(req, res, next) {
  try {
    success(res, "Kategori laptop berhasil dibuat", await service.create(req.validated.body), 201);
  } catch (err) {
    next(err);
  }
}

async function update(req, res, next) {
  try {
    success(res, "Kategori laptop berhasil diperbarui", await service.update(req.validated.params.id, req.validated.body));
  } catch (err) {
    next(err);
  }
}

async function remove(req, res, next) {
  try {
    await service.remove(req.validated.params.id);
    success(res, "Kategori laptop berhasil dihapus");
  } catch (err) {
    next(err);
  }
}

module.exports = { list, create, update, remove };
