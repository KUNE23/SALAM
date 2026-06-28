const prisma = require("../config/prisma");

async function get() {
  const setting = await prisma.setting.findFirst();
  if (setting) return setting;
  return prisma.setting.create({ data: {} });
}

async function update(body) {
  const setting = await get();
  return prisma.setting.update({ where: { id: setting.id }, data: { appName: body.appName, openTime: body.openTime, closeTime: body.closeTime, maxLoanDays: body.maxLoanDays } });
}

module.exports = { get, update };
