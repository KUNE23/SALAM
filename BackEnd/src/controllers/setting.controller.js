const service = require("../services/setting.service");
const { success } = require("../utils/response");

async function get(req, res, next) {
  try {
    success(res, "Berhasil mengambil pengaturan", await service.get());
  } catch (err) {
    next(err);
  }
}

async function update(req, res, next) {
  try {
    success(res, "Pengaturan berhasil diperbarui", await service.update(req.validated.body));
  } catch (err) {
    next(err);
  }
}

module.exports = { get, update };
