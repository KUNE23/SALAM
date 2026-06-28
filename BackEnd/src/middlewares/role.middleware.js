const { AppError } = require("../utils/response");

function allowRoles(...roles) {
  return (req, res, next) => {
    if (!req.user || !roles.includes(req.user.role)) return next(new AppError("Akses ditolak", 403));
    return next();
  };
}

module.exports = allowRoles;
