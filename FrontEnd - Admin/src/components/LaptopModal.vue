<script setup>
import { Save, Upload, X } from "lucide-vue-next";
import { reactive } from "vue";

const emit = defineEmits(["close", "save"]);
const form = reactive({ brand: "", series: "", code: "", completeness: "Laptop dan charger", ram: "Ram 128-55", photo: null });

function submit() {
  const data = new FormData();
  data.append("brand", form.brand);
  data.append("series", form.series);
  data.append("code", form.code);
  data.append("completeness", form.completeness);
  data.append("ram", form.ram);
  if (form.photo) data.append("photo", form.photo);
  emit("save", data);
}
</script>

<template>
  <form class="modal" @submit.prevent="submit">
    <div class="modal-header"><h2>Tambah Laptop</h2><button type="button" @click="emit('close')"><X /></button></div>
    <div class="modal-body">
      <label>Merk</label><input v-model="form.brand" placeholder="Lenovo Thinkpad" required>
      <label>Seri</label><input v-model="form.series" placeholder="EMP-001" required>
      <label>Kode</label><input v-model="form.code" placeholder="LAPTOP09" required>
      <label>Kelengkapan</label><input v-model="form.completeness" placeholder="Pilih Kelengkapan">
      <label>foto</label><label class="upload-box"><Upload :size="34" />Upload dokumen (opsional)<span>PDF, JPG, PNG maks. 5MB</span><input type="file" @change="form.photo = $event.target.files?.[0]"></label>
      <div class="modal-footer"><button type="button" @click="emit('close')">Batal</button><button class="blue"><Save :size="16" />Simpan Laptop</button></div>
    </div>
  </form>
</template>
