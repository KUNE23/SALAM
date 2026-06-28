const app = require("./app");
const env = require("./config/env");
const prisma = require("./config/prisma");

const server = app.listen(env.port, () => {
  console.log(`SALAM API berjalan di port ${env.port}`);
});

async function shutdown(signal) {
  console.log(`${signal} diterima, menutup server`);
  server.close(async () => {
    await prisma.$disconnect();
    process.exit(0);
  });
}

process.on("SIGINT", () => shutdown("SIGINT"));
process.on("SIGTERM", () => shutdown("SIGTERM"));
process.on("unhandledRejection", async (reason) => {
  console.error(reason);
  await prisma.$disconnect();
  process.exit(1);
});
