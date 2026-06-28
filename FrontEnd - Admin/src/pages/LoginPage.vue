<script setup>
import { Eye, Lock, Mail } from "lucide-vue-next";
import { reactive, ref } from "vue";
import LogoMark from "../components/LogoMark.vue";

const props = defineProps({
  onLogin: Function
});
const loading = ref(false);
const form = reactive({ email: "admin@absenku.com", password: "admin123", error: "" });

async function submit() {
  loading.value = true;
  form.error = "";
  try {
    await props.onLogin({ email: form.email, password: form.password });
  } catch (error) {
    form.error = error.message || "Login gagal";
  } finally {
    loading.value = false;
  }
}
</script>

<template>
  <section class="login-page">
    <div class="school-badge">Support by<br><b>SMK ISLAM MADANI</b></div>
    <div class="login-brand"><LogoMark large /></div>
    <form class="login-card" @submit.prevent="submit">
      <h1>Admin Login</h1>
      <p>Masuk ke panel administrasi</p>
      <label>Email Admin</label>
      <div class="login-input"><Mail :size="20" /><input v-model="form.email"></div>
      <label>Password</label>
      <div class="login-input"><Lock :size="20" /><input v-model="form.password" type="password"><Eye :size="18" /></div>
      <div v-if="form.error" class="form-error">{{ form.error }}</div>
      <button class="primary-login" :disabled="loading">{{ loading ? "Memproses..." : "Masuk ke Dashboard" }}</button>
    </form>
  </section>
</template>
