const service = require("../services/profile.service");
const { success } = require("../utils/response");

async function get(req, res, next) {
  try {
    success(res, "Berhasil mengambil profil", await service.get(req.user.id));
  } catch (err) {
    next(err);
  }
}

async function update(req, res, next) {
  try {
    success(res, "Profil berhasil diperbarui", await service.update(req.user.id, req.validated.body));
  } catch (err) {
    next(err);
  }
}

async function password(req, res, next) {
  try {
    await service.changePassword(req.user.id, req.validated.body.currentPassword, req.validated.body.newPassword);
    success(res, "Password berhasil diperbarui");
  } catch (err) {
    next(err);
  }
}

module.exports = { get, update, password };
