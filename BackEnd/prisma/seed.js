const argon2 = require("argon2");
const { PrismaClient } = require("@prisma/client");

const prisma = new PrismaClient();

async function main() {
  const adminPassword = await argon2.hash("admin123");
  const siswaPassword = await argon2.hash("siswa123");

  await prisma.setting.upsert({
    where: { id: "default-setting" },
    update: {},
    create: { id: "default-setting" }
  });

  await prisma.user.upsert({
    where: { email: "admin@absenku.com" },
    update: {},
    create: { name: "Admin User", email: "admin@absenku.com", password: adminPassword, role: "SUPER_ADMIN" }
  });

  const students = [
    ["Yoga Nugraha", "yoganugraha@gmail.com", "12345", "12 RPL", null, "ACTIVE"],
    ["Wita", "wita@gmail.com", "12506", "12 DKV", null, "ACTIVE"],
    ["Pani", "pani@gmail.com", "56787", "11 DKV", null, "ACTIVE"],
    ["Serli", "serli@gmail.com", "06346", "Packing", null, "ACTIVE"],
    ["Dudung", "udung@gmail.com", "32674", "Packing", null, "INACTIVE"],
    ["Asep", "asep@gmail.com", "79358", "Desain Fashion", null, "ACTIVE"]
  ];

  for (const [name, email, nisn, className, major, status] of students) {
    await prisma.user.upsert({
      where: { email },
      update: {},
      create: { name, email, password: siswaPassword, role: "SISWA", nisn, className, major, status }
    });
  }

  for (let i = 1; i <= 8; i += 1) {
    await prisma.laptop.upsert({
      where: { code: `LAPTOP${String(i).padStart(2, "0")}` },
      update: {},
      create: {
        brand: "Lenovo",
        series: `Thinkpad ${String(i).padStart(2, "0")}`,
        code: `LAPTOP${String(i).padStart(2, "0")}`,
        ram: "128-55"
      }
    });
  }
}

main()
  .finally(async () => prisma.$disconnect())
  .catch(async (error) => {
    console.error(error);
    await prisma.$disconnect();
    process.exit(1);
  });
