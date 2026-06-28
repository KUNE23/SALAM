const env = require("./env");

const corsOptions = {
  origin(origin, callback) {
    if (!origin || env.corsOrigin.includes(origin) || isLocalDevOrigin(origin)) return callback(null, true);
    return callback(new Error("Origin tidak diizinkan"));
  },
  credentials: true
};

function isLocalDevOrigin(origin) {
  if (env.nodeEnv !== "development") return false;

  try {
    const url = new URL(origin);
    const isLocalHost = ["localhost", "127.0.0.1"].includes(url.hostname);
    const isDevPort = ["5173", "5174", "3000"].includes(url.port);
    return isLocalHost && isDevPort;
  } catch {
    return false;
  }
}

module.exports = corsOptions;
