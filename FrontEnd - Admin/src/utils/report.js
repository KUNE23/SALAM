function esc(value) {
  return String(value ?? "").replace(/[&<>"']/g, (char) => ({
    "&": "&amp;",
    "<": "&lt;",
    ">": "&gt;",
    '"': "&quot;",
    "'": "&#039;"
  }[char]));
}

export function openDashboardReport({ summary, laptops, loans, students, setting }) {
  const borrowed = laptops.filter((item) => item.status === "BORROWED").length;
  const available = laptops.filter((item) => item.status === "AVAILABLE").length;
  const pending = loans.filter((item) => item.status === "PENDING").length;
  const overdue = loans.filter((item) => item.status === "OVERDUE").length;
  const now = new Date().toLocaleString("id-ID", { dateStyle: "full", timeStyle: "short" });

  const rows = [
    ["Total Laptop", summary?.totalLaptops ?? laptops.length],
    ["Laptop Tersedia", summary?.availableLaptops ?? available],
    ["Laptop Dipinjam", summary?.borrowedLaptops ?? borrowed],
    ["Permintaan Menunggu", pending],
    ["Pengembalian Terlambat", overdue],
    ["Total Siswa", summary?.totalStudents ?? students.length]
  ];
  const loanRows = loans.length
    ? loans.map((item, index) => `
      <tr>
        <td>${index + 1}</td>
        <td>${esc(item.student?.name || item.user?.name || "-")}</td>
        <td>${esc(item.student?.email || item.user?.emailOrPhone || item.user?.email || "-")}</td>
        <td>${esc(item.laptop?.code || "-")}</td>
        <td>${esc(item.laptop?.name || [item.laptop?.brand, item.laptop?.series].filter(Boolean).join(" ") || "-")}</td>
        <td>${esc(item.requestDate || "-")}</td>
        <td>${esc(item.startTime || "-")}</td>
        <td>${esc(item.returnTime || item.returnDeadline || "-")}</td>
        <td>${esc(item.duration || "-")}</td>
        <td>${esc(item.status || "-")}</td>
      </tr>
    `).join("")
    : `<tr><td colspan="10" class="empty">Belum ada data peminjaman laptop.</td></tr>`;

  const html = `<!doctype html>
<html>
<head>
  <meta charset="utf-8">
  <title>Laporan SALAM</title>
  <style>
    @page { size: A4; margin: 18mm; }
    body { font-family: Arial, sans-serif; color: #172033; margin: 0; }
    .head { display: flex; justify-content: space-between; border-bottom: 2px solid #0b6fa7; padding-bottom: 16px; margin-bottom: 22px; }
    h1 { margin: 0 0 6px; font-size: 26px; }
    h2 { margin: 28px 0 10px; font-size: 18px; }
    p { margin: 0; color: #526579; }
    table { width: 100%; border-collapse: collapse; margin-top: 18px; }
    th, td { border: 1px solid #dbe4ef; padding: 9px 10px; text-align: left; vertical-align: top; font-size: 12px; }
    th { background: #eef4fb; }
    .grid { display: grid; grid-template-columns: repeat(2, 1fr); gap: 12px; margin-top: 18px; }
    .card { border: 1px solid #dbe4ef; padding: 14px; border-radius: 8px; }
    .card b { display: block; font-size: 24px; margin-top: 8px; }
    .footer { margin-top: 32px; font-size: 12px; color: #64748b; }
    @media print { .no-print { display: none; } }
    .no-print { margin: 18px 0; }
    .empty { text-align: center; color: #64748b; padding: 24px; }
    button { background: #096ca4; color: white; border: 0; border-radius: 6px; padding: 10px 18px; font-weight: 700; }
  </style>
</head>
<body>
  <div class="head">
    <div>
      <h1>${esc(setting?.appName || "SALAM PEMINJAMAN LAPTOP")}</h1>
      <p>Laporan peminjaman laptop</p>
    </div>
    <p>${esc(now)}</p>
  </div>
  <div class="no-print"><button onclick="window.print()">Print / Simpan PDF</button></div>
  <div class="grid">
    ${rows.map(([label, value]) => `<div class="card">${esc(label)}<b>${esc(value)}</b></div>`).join("")}
  </div>
  <h2>Data Peminjaman Laptop</h2>
  <table>
    <thead>
      <tr>
        <th>No</th>
        <th>Nama Siswa</th>
        <th>Email</th>
        <th>Kode Laptop</th>
        <th>Nama Laptop</th>
        <th>Tanggal</th>
        <th>Jam Pinjam</th>
        <th>Batas Kembali</th>
        <th>Durasi</th>
        <th>Status</th>
      </tr>
    </thead>
    <tbody>${loanRows}</tbody>
  </table>
  <p class="footer">Dokumen ini dibuat otomatis oleh SALAM Admin dan siap dicetak sebagai PDF.</p>
</body>
</html>`;

  const blob = new Blob([html], { type: "text/html;charset=utf-8" });
  const url = URL.createObjectURL(blob);
  const report = window.open(url, "_blank");
  if (!report) URL.revokeObjectURL(url);
  window.setTimeout(() => URL.revokeObjectURL(url), 60000);
}
