const path = require("path");
const crypto = require("crypto");
const multer = require("multer");
const env = require("../config/env");
const { AppError } = require("../utils/response");

const allowed = new Set([".jpg", ".jpeg", ".png", ".webp"]);

const storage = multer.diskStorage({
  destination: env.uploadDir,
  filename(req, file, cb) {
    const ext = path.extname(file.originalname).toLowerCase();
    cb(null, `${crypto.randomBytes(16).toString("hex")}${ext}`);
  }
});

const upload = multer({
  storage,
  limits: { fileSize: env.maxFileSizeMb * 1024 * 1024 },
  fileFilter(req, file, cb) {
    const ext = path.extname(file.originalname).toLowerCase();
    if (!allowed.has(ext)) return cb(new AppError("Format file tidak valid", 422));
    return cb(null, true);
  }
});

module.exports = upload;
