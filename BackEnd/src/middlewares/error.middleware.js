const env = require("../config/env");
const { error } = require("../utils/response");

function notFound(req, res, next) {
  next(Object.assign(new Error("Endpoint tidak ditemukan"), { statusCode: 404 }));
}

function errorHandler(err, req, res, next) {
  const statusCode = err.statusCode || 500;
  const errors = err.errors || (env.nodeEnv === "production" ? undefined : err.stack);
  return error(res, err.message || "Terjadi kesalahan server", statusCode, errors);
}

module.exports = { notFound, errorHandler };
