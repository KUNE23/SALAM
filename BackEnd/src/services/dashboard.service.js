const prisma = require("../config/prisma");

async function summary() {
  const [totalLaptop, borrowedLaptop, pendingRequests, overdueReturns, activeStudents, totalStudents] = await Promise.all([
    prisma.laptop.count(),
    prisma.laptop.count({ where: { status: "BORROWED" } }),
    prisma.loan.count({ where: { status: "PENDING" } }),
    prisma.loan.count({ where: { status: "OVERDUE" } }),
    prisma.user.count({ where: { role: "SISWA", status: "ACTIVE" } }),
    prisma.user.count({ where: { role: "SISWA" } })
  ]);
  const activeStudentPercentage = totalStudents ? Math.round((activeStudents / totalStudents) * 100) : 0;
  return { totalLaptop, borrowedLaptop, pendingRequests, overdueReturns, activeStudentPercentage, weeklyLoanActivity: [0, 0, 0, 0, 0, 0, 0] };
}

module.exports = { summary };
