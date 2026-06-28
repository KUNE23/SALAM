const express = require("express");
const morgan = require("morgan");
const path = require("path");
const env = require("./config/env");
const routes = require("./routes");
const { security, sanitizeRequests } = require("./middlewares/security.middleware");
const { notFound, errorHandler } = require("./middlewares/error.middleware");
const { success } = require("./utils/response");

const app = express();

security(app);
app.use(express.json({ limit: "10kb" }));
app.use(express.urlencoded({ extended: false, limit: "10kb" }));
sanitizeRequests(app);
app.use(morgan(env.nodeEnv === "production" ? "combined" : "dev"));
app.use("/uploads", express.static(path.resolve(env.uploadDir)));

app.get("/health", (req, res) => success(res, "SALAM API aktif", { uptime: process.uptime() }));
app.use("/api", routes);
app.use(notFound);
app.use(errorHandler);

module.exports = app;
