<script setup>
import { Save, X } from "lucide-vue-next";
import { reactive } from "vue";

const emit = defineEmits(["close", "save"]);
const form = reactive({ name: "", description: "", unitCount: 0, status: "ACTIVE" });
</script>

<template>
  <form class="modal" @submit.prevent="emit('save', { ...form })">
    <div class="modal-header"><h2>Tambah Kategori</h2><button type="button" @click="emit('close')"><X /></button></div>
    <div class="modal-body">
      <label>Nama Kategori</label><input v-model="form.name" placeholder="High Performance" required>
      <label>Deskripsi</label><input v-model="form.description" placeholder="Workstation & Editing">
      <div class="two-cols"><div><label>Jumlah Unit</label><input v-model.number="form.unitCount" type="number" min="0"></div><div><label>Status</label><select v-model="form.status"><option value="ACTIVE">Aktif</option><option value="INACTIVE">Penuh</option></select></div></div>
      <div class="modal-footer"><button type="button" @click="emit('close')">Batal</button><button class="blue"><Save :size="16" />Simpan Kategori</button></div>
    </div>
  </form>
</template>
