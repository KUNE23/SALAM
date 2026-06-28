const { AppError } = require("../utils/response");

function validate(schema) {
  return (req, res, next) => {
    const result = schema.safeParse({ body: req.body || {}, query: req.query || {}, params: req.params || {} });
    if (!result.success) {
      return next(new AppError("Validasi gagal", 422, result.error.flatten()));
    }
    req.validated = result.data;
    return next();
  };
}

module.exports = validate;
