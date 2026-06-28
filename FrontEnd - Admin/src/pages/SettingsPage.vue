<script setup>
import { Bell, FileText, Grid2X2, Info, MoreVertical, PackageCheck, Plus, Settings, ShieldCheck, User } from "lucide-vue-next";
import PageHeader from "../components/PageHeader.vue";

defineProps({
  user: { type: Object, required: true },
  setting: { type: Object, required: true },
  categories: { type: Array, default: () => [] }
});

const emit = defineEmits(["save-profile", "save-settings", "add-category"]);

function categoryIcon(name) {
  if (name === "Programming") return PackageCheck;
  if (name === "Standard Office") return FileText;
  return Grid2X2;
}
</script>

<template>
  <section class="content">
    <PageHeader title="Pengaturan" subtitle="Kelola preferensi akun, sistem, dan konfigurasi inventaris Anda." />
    <div class="settings-grid">
      <div class="settings-left">
        <section class="card profile-card">
          <h2><span><User /></span>Profil Admin</h2>
          <div class="photo-box"></div>
          <h3>{{ user.name || 'Budi Santoso' }}</h3>
          <p>{{ user.emailOrPhone || user.email }}</p>
          <label>Nama Lengkap</label>
          <input v-model="user.name">
          <label>Nomor Induk Pegawai</label>
          <input value="19890422 201201 1 002" disabled>
          <button @click="emit('save-profile')">Perbarui Profil</button>
        </section>
        <section class="card notify-card">
          <h2><span><Bell /></span>Notifikasi</h2>
          <div class="toggle-row"><div><b>Email Peminjaman Baru</b><p>Terima email saat ada request baru</p></div><span class="toggle on"></span></div>
          <div class="toggle-row"><div><b>Pengingat Jatuh Tempo</b><p>Notifikasi 1 hari sebelum batas waktu</p></div><span class="toggle on"></span></div>
        </section>
      </div>

      <div class="settings-right">
        <section class="card system-card">
          <h2><span><Settings /></span><div>Pengaturan Sistem<p>Konfigurasi operasional aplikasi</p></div></h2>
          <label>NAMA APLIKASI</label>
          <input v-model="setting.appName">
          <div class="inline-fields">
            <div><label>JAM BUKA LAYANAN</label><input v-model="setting.openTime"></div>
            <b>s/d</b>
            <input v-model="setting.closeTime">
            <div><label>MAKSIMAL HARI PEMINJAMAN</label><input v-model.number="setting.maxLoanDays"></div>
          </div>
          <button @click="emit('save-settings')">Simpan Perubahan Sistem</button>
        </section>

        <section class="card category-card">
          <div class="category-head">
            <span><Grid2X2 /></span>
            <div><h2>Kategori Laptop</h2><p>Klasifikasi inventaris berdasarkan spek</p></div>
            <button @click="emit('add-category')"><Plus :size="18" />Kategori Baru</button>
          </div>
          <table class="category-table">
            <thead><tr><th>NAMA KATEGORI</th><th>JUMLAH UNIT</th><th>STATUS</th><th>AKSI</th></tr></thead>
            <tbody>
              <tr v-for="item in categories" :key="item.id">
                <td><div class="category-name"><span><component :is="categoryIcon(item.name)" :size="20" /></span><div><b>{{ item.name }}</b><p>{{ item.description }}</p></div></div></td>
                <td>{{ item.unitCount }} Unit</td>
                <td><span :class="item.status === 'ACTIVE' ? 'status active' : 'status full'">{{ item.status === 'ACTIVE' ? 'Aktif' : 'Penuh' }}</span></td>
                <td><MoreVertical :size="18" /></td>
              </tr>
            </tbody>
          </table>
        </section>

        <section class="card security-card">
          <div><span><ShieldCheck /></span><div><h2>Keamanan & Akses</h2><p>Pengaturan otentikasi dan izin akses</p></div></div>
          <button>Reset Password</button>
          <p class="access-note"><Info :size="18" />Terakhir diakses: 12 Oktober 2023, 14:22 WIB dari Jakarta, Indonesia.</p>
        </section>
      </div>
    </div>
  </section>
</template>
