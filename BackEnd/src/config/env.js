const dotenv = require("dotenv");

dotenv.config();

const env = {
  port: Number(process.env.PORT || 5000),
  nodeEnv: process.env.NODE_ENV || "development",
  dbUrl: process.env.DB_URL,
  jwtAccessSecret: process.env.JWT_ACCESS_SECRET || "change-this-access-secret",
  jwtRefreshSecret: process.env.JWT_REFRESH_SECRET || "change-this-refresh-secret",
  jwtAccessExpiresIn: process.env.JWT_ACCESS_EXPIRES_IN || "15m",
  jwtRefreshExpiresIn: process.env.JWT_REFRESH_EXPIRES_IN || "7d",
  corsOrigin: (process.env.CORS_ORIGIN || "").split(",").map((item) => item.trim()).filter(Boolean),
  uploadDir: process.env.UPLOAD_DIR || "uploads",
  maxFileSizeMb: Number(process.env.MAX_FILE_SIZE_MB || 5)
};

module.exports = env;
