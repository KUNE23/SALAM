<script setup>
import { Save, X } from "lucide-vue-next";
import { reactive } from "vue";

const emit = defineEmits(["close", "save"]);

const form = reactive({ name: "", email: "", nisn: "", className: "", status: "ACTIVE", password: "password123" });

function submit() {
  emit("save", {
    name: form.name,
    emailOrPhone: form.email,
    nisn: form.nisn,
    className: form.className,
    status: form.status,
    password: form.password,
    role: "SISWA"
  });
}
</script>

<template>
  <form class="modal" @submit.prevent="submit">
    <div class="modal-header"><h2>Tambah Siswa</h2><button type="button" @click="emit('close')"><X /></button></div>
    <div class="modal-body">
      <label>Nama Lengkap</label><input v-model="form.name" placeholder="Andi Prasetyo" required>
      <div class="two-cols"><div><label>NISN</label><input v-model="form.nisn" placeholder="123134"></div><div><label>Kelas</label><input v-model="form.className" placeholder="Pilih"></div></div>
      <label>Email</label><input v-model="form.email" placeholder="siswa@email.com" required>
      <label>Status</label><select v-model="form.status"><option value="ACTIVE">Aktif</option><option value="INACTIVE">Nonaktif</option></select>
      <div class="admin-note"><b>Catatan Administrasi</b><br>Pastikan NISN sesuai dengan data PDDIKTI untuk sinkronisasi yang tepat.</div>
      <div class="modal-footer"><button type="button" @click="emit('close')">Batal</button><button class="blue"><Save :size="16" />Simpan Siswa</button></div>
    </div>
  </form>
</template>
