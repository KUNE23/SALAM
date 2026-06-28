<script setup>
import { computed, onBeforeUnmount, onMounted, ref } from "vue";
import { api, authStore } from "./api/client";
import AdminLayout from "./components/AdminLayout.vue";
import CategoryModal from "./components/CategoryModal.vue";
import LaptopModal from "./components/LaptopModal.vue";
import StudentModal from "./components/StudentModal.vue";
import DashboardPage from "./pages/DashboardPage.vue";
import InventoryPage from "./pages/InventoryPage.vue";
import LoansPage from "./pages/LoansPage.vue";
import LoginPage from "./pages/LoginPage.vue";
import SettingsPage from "./pages/SettingsPage.vue";
import UsersPage from "./pages/UsersPage.vue";

const page = ref("dashboard");
const user = ref(null);
const summary = ref(null);
const students = ref([]);
const laptops = ref([]);
const loans = ref([]);
const setting = ref(null);
const categories = ref([]);
const modal = ref(null);
const toast = ref("");
const loading = ref(false);
let refreshTimer;

const canShowSettings = computed(() => user.value && setting.value);

onMounted(async () => {
  if (authStore.token) {
    try {
      user.value = await api.me();
      await loadAll();
      startAutoRefresh();
    } catch {
      authStore.clear();
    }
  }
});

onBeforeUnmount(() => window.clearInterval(refreshTimer));

async function login({ email, password }) {
  const result = await api.login(email, password);
  authStore.save(result.accessToken, result.refreshToken);
  user.value = result.user;
  await loadAll();
  startAutoRefresh();
}

async function loadAll() {
  loading.value = true;
  try {
    const [summaryData, studentData, laptopData, loanData, settingData, categoryData] = await Promise.all([
      api.summary(),
      api.students("?limit=100"),
      api.laptops(),
      api.loans(),
      api.settings(),
      api.laptopCategories()
    ]);
    summary.value = summaryData;
    students.value = studentData;
    laptops.value = laptopData;
    loans.value = loanData;
    setting.value = settingData;
    categories.value = categoryData;
  } finally {
    loading.value = false;
  }
}

function startAutoRefresh() {
  window.clearInterval(refreshTimer);
  refreshTimer = window.setInterval(() => {
    loadAll().catch(() => null);
  }, 30000);
}

async function run(action, message) {
  loading.value = true;
  try {
    await action();
    await loadAll();
    showToast(message);
    return true;
  } catch (error) {
    showToast(error.message || "Terjadi kesalahan");
    return false;
  } finally {
    loading.value = false;
  }
}

function showToast(message) {
  toast.value = message;
  window.setTimeout(() => {
    toast.value = "";
  }, 2600);
}

function logout() {
  api.logout().catch(() => null);
  authStore.clear();
  user.value = null;
  page.value = "dashboard";
  window.clearInterval(refreshTimer);
}

async function saveStudent(body) {
  if (await run(() => api.createStudent(body), "Siswa ditambahkan")) modal.value = null;
}

async function saveLaptop(formData) {
  if (await run(() => api.createLaptop(formData), "Laptop ditambahkan")) modal.value = null;
}

async function saveCategory(body) {
  if (await run(() => api.createLaptopCategory(body), "Kategori ditambahkan")) modal.value = null;
}
</script>

<template>
  <LoginPage v-if="!user" :on-login="login" />
  <AdminLayout v-else :page="page" :user="user" @navigate="page = $event" @logout="logout">
    <DashboardPage
      v-if="page === 'dashboard'"
      :summary="summary"
      :laptops="laptops"
      :loans="loans"
      :students="students"
      :setting="setting"
      @refresh="loadAll"
    />
    <InventoryPage v-else-if="page === 'inventory'" :laptops="laptops" @add="modal = 'laptop'" />
    <LoansPage
      v-else-if="page === 'loans'"
      :loans="loans"
      @approve="(id) => run(() => api.approveLoan(id), 'Pengajuan disetujui')"
      @reject="(id) => run(() => api.rejectLoan(id), 'Pengajuan ditolak')"
    />
    <UsersPage
      v-else-if="page === 'users'"
      :students="students"
      @add="modal = 'student'"
      @delete="(id) => run(() => api.deleteStudent(id), 'Siswa dihapus')"
      @toggle="(item) => run(() => api.setStudentStatus(item.id, item.status === 'ACTIVE' ? 'INACTIVE' : 'ACTIVE'), 'Status diperbarui')"
    />
    <SettingsPage
      v-else-if="page === 'settings' && canShowSettings"
      :user="user"
      :setting="setting"
      :categories="categories"
      @save-profile="run(() => api.updateProfile({ name: user.name }), 'Profil diperbarui')"
      @save-settings="run(() => api.updateSettings(setting), 'Pengaturan disimpan')"
      @add-category="modal = 'category'"
    />
  </AdminLayout>

  <div v-if="modal" class="modal-backdrop">
    <StudentModal v-if="modal === 'student'" @close="modal = null" @save="saveStudent" />
    <LaptopModal v-else-if="modal === 'laptop'" @close="modal = null" @save="saveLaptop" />
    <CategoryModal v-else @close="modal = null" @save="saveCategory" />
  </div>
  <div v-if="toast" class="toast">{{ toast }}</div>
  <div v-if="loading" class="loading-bar"></div>
</template>
