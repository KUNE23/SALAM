const service = require("../services/dashboard.service");
const { success } = require("../utils/response");

async function summary(req, res, next) {
  try {
    success(res, "Berhasil mengambil ringkasan", await service.summary());
  } catch (err) {
    next(err);
  }
}

module.exports = { summary };
