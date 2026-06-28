const API_BASE_URL = import.meta.env.VITE_API_BASE_URL || "http://localhost:5000/api";
const tokenKey = "salam_admin_access_token";
const refreshKey = "salam_admin_refresh_token";

export const authStore = {
  get token() {
    return localStorage.getItem(tokenKey);
  },
  save(accessToken, refreshToken) {
    localStorage.setItem(tokenKey, accessToken);
    if (refreshToken) localStorage.setItem(refreshKey, refreshToken);
  },
  clear() {
    localStorage.removeItem(tokenKey);
    localStorage.removeItem(refreshKey);
  }
};

async function request(path, options = {}, retry = true) {
  const headers = new Headers(options.headers);
  if (!(options.body instanceof FormData)) headers.set("Content-Type", "application/json");
  if (authStore.token) headers.set("Authorization", `Bearer ${authStore.token}`);

  const response = await fetch(`${API_BASE_URL}${path}`, { ...options, headers });
  const json = await response.json().catch(() => null);

  if (response.status === 401 && retry) {
    const refreshed = await refreshToken();
    if (refreshed) return request(path, options, false);
  }

  if (!response.ok || !json?.success) throw new Error(json?.message || errorMessage(response.status));
  return json.data;
}

async function refreshToken() {
  const refreshTokenValue = localStorage.getItem(refreshKey);
  if (!refreshTokenValue) return false;

  const response = await fetch(`${API_BASE_URL}/auth/refresh`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ refreshToken: refreshTokenValue })
  });
  const json = await response.json().catch(() => null);

  if (!response.ok || !json?.data?.accessToken) {
    authStore.clear();
    return false;
  }

  authStore.save(json.data.accessToken, json.data.refreshToken);
  return true;
}

function errorMessage(code) {
  if (code === 403) return "Akses ditolak";
  if (code === 404) return "Data tidak ditemukan";
  if (code === 409) return "Data sedang konflik";
  if (code >= 500) return "Server sedang bermasalah";
  return "Terjadi kesalahan";
}

export const api = {
  login: (email, password) =>
    request("/auth/login", {
      method: "POST",
      body: JSON.stringify({ email, password })
    }),
  logout: () => request("/auth/logout", { method: "POST" }).finally(() => authStore.clear()),
  me: () => request("/auth/me"),
  profile: () => request("/profile"),
  updateProfile: (body) => request("/profile", { method: "PUT", body: JSON.stringify(body) }),
  summary: () => request("/dashboard/summary"),
  students: (query = "") => request(`/students${query}`),
  createStudent: (body) => request("/students", { method: "POST", body: JSON.stringify(body) }),
  updateStudent: (id, body) => request(`/students/${id}`, { method: "PUT", body: JSON.stringify(body) }),
  deleteStudent: (id) => request(`/students/${id}`, { method: "DELETE", body: JSON.stringify({}) }),
  setStudentStatus: (id, status) =>
    request(`/students/${id}/status`, { method: "PATCH", body: JSON.stringify({ status }) }),
  laptops: () => request("/laptops?limit=100"),
  createLaptop: (body) => request("/laptops", { method: "POST", body }),
  deleteLaptop: (id) => request(`/laptops/${id}`, { method: "DELETE" }),
  loans: () => request("/loans?limit=100"),
  approveLoan: (id) => request(`/loans/${id}/approve`, { method: "PATCH" }),
  rejectLoan: (id) => request(`/loans/${id}/reject`, { method: "PATCH", body: JSON.stringify({ reason: "Tidak disetujui" }) }),
  returnLoan: (id) => request(`/loans/${id}/return`, { method: "PATCH" }),
  settings: () => request("/settings"),
  updateSettings: (body) => request("/settings", { method: "PUT", body: JSON.stringify(body) }),
  laptopCategories: () => request("/laptop-categories"),
  createLaptopCategory: (body) => request("/laptop-categories", { method: "POST", body: JSON.stringify(body) }),
  updateLaptopCategory: (id, body) => request(`/laptop-categories/${id}`, { method: "PUT", body: JSON.stringify(body) }),
  deleteLaptopCategory: (id) => request(`/laptop-categories/${id}`, { method: "DELETE" })
};
