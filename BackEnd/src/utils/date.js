function pad(value) {
  return String(value).padStart(2, "0");
}

function formatDate(date) {
  if (!date) return null;
  const d = new Date(date);
  return `${d.getFullYear()}-${pad(d.getMonth() + 1)}-${pad(d.getDate())}`;
}

function formatTime(date) {
  if (!date) return null;
  const d = new Date(date);
  return `${pad(d.getHours())}:${pad(d.getMinutes())}`;
}

function deadlineFromSetting(baseDate, closeTime, maxLoanDays) {
  const [hour, minute] = closeTime.split(":").map(Number);
  const date = new Date(baseDate);
  date.setDate(date.getDate() + Math.max(maxLoanDays - 1, 0));
  date.setHours(hour, minute, 0, 0);
  return date;
}

function loanDuration(start, end) {
  if (!start || !end) return null;
  const days = Math.max(Math.ceil((new Date(end) - new Date(start)) / 86400000), 1);
  return `${days} hari`;
}

function initials(name) {
  return name.split(" ").filter(Boolean).slice(0, 2).map((item) => item[0].toUpperCase()).join("");
}

module.exports = { formatDate, formatTime, deadlineFromSetting, loanDuration, initials };
