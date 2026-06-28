const helmet = require("helmet");
const cors = require("cors");
const rateLimit = require("express-rate-limit");
const mongoSanitize = require("express-mongo-sanitize");
const hpp = require("hpp");
const corsOptions = require("../config/cors");

const globalLimiter = rateLimit({ windowMs: 15 * 60 * 1000, limit: 300, standardHeaders: true, legacyHeaders: false });
const loginLimiter = rateLimit({ windowMs: 15 * 60 * 1000, limit: 10, standardHeaders: true, legacyHeaders: false });

function security(app) {
  app.use(helmet());
  app.use(cors(corsOptions));
  app.use(globalLimiter);
}

function sanitizeRequests(app) {
  app.use(mongoSanitize());
  app.use(hpp());
}

module.exports = { security, sanitizeRequests, loginLimiter };
