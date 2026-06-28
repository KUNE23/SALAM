<script setup>
import { ChevronDown, Edit, Filter, Plus, Search, Trash2 } from "lucide-vue-next";
import PageHeader from "../components/PageHeader.vue";

defineProps({
  students: { type: Array, default: () => [] }
});

const emit = defineEmits(["add", "delete", "toggle"]);

function initials(name = "AP") {
  return name.split(" ").map((part) => part[0]).join("").slice(0, 2).toUpperCase();
}
</script>

<template>
  <section class="content">
    <PageHeader title="Manajemen SISWA" :subtitle="`${students.length} siswa terdaftar`">
      <button class="add-user" @click="emit('add')"><Plus :size="18" />Tambah User</button>
    </PageHeader>
    <div class="card filter-card"><div class="table-search"><Search :size="16" />Cari nama atau ID...</div><button>Kelas <ChevronDown :size="14" /></button><button><Filter :size="17" />Filter</button></div>
    <section class="card table-card">
      <table>
        <thead><tr><th>NAMA</th><th>NISN</th><th>KELAS</th><th>STATUS</th><th>AKSI</th></tr></thead>
        <tbody>
          <tr v-for="item in students" :key="item.id">
            <td><div class="user-cell"><span class="avatar">{{ initials(item.name) }}</span><div><b>{{ item.name }}</b><small>{{ item.emailOrPhone }}</small></div></div></td>
            <td>{{ item.nisn || '-' }}</td>
            <td>{{ item.className || '-' }}</td>
            <td><span :class="item.status === 'ACTIVE' ? 'status active' : 'status inactive'">{{ item.status === 'ACTIVE' ? 'Aktif' : 'Nonaktif' }}</span></td>
            <td><div class="row-actions"><Edit :size="18" @click="emit('toggle', item)" /><Trash2 :size="18" @click="emit('delete', item.id)" /></div></td>
          </tr>
        </tbody>
      </table>
      <div class="table-footer">Menampilkan {{ students.length }} dari {{ students.length }} siswa <span>&lt;</span><b>1</b></div>
    </section>
  </section>
</template>
