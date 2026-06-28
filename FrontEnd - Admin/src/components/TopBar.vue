<script setup>
import { Bell, CircleHelp, LogOut, Search, Settings, Shield, User } from "lucide-vue-next";
import { ref } from "vue";

defineProps({
  user: { type: Object, required: true }
});

defineEmits(["logout", "settings"]);

const profileOpen = ref(false);
</script>

<template>
  <header class="topbar">
    <div class="global-search"><Search :size="20" /><span>Cari peminjam, aset, atau siswa...</span></div>
    <Bell :size="21" />
    <CircleHelp :size="23" />
    <div class="profile-menu-wrap">
      <button class="admin-profile" @click="profileOpen = !profileOpen">
        <b>{{ user.name || "Admin User" }}</b>
        <small>SUPER ADMINISTRATOR</small>
        <span></span>
      </button>
      <div v-if="profileOpen" class="profile-popover">
        <div class="popover-arrow"></div>
        <div class="profile-head">
          <div class="big-avatar"><User :size="54" /></div>
          <h3>{{ user.name || "Admin Utama" }}</h3>
          <span class="role-pill"><Shield :size="13" />SUPER ADMIN</span>
          <p>{{ user.emailOrPhone || user.email || "admin.utama@absenku.pro" }}</p>
        </div>
        <button class="profile-row"><User :size="20" />Lihat Profil</button>
        <button class="profile-row" @click="$emit('settings'); profileOpen = false"><Settings :size="20" />Pengaturan Akun</button>
        <button class="logout-wide" @click="$emit('logout')"><LogOut :size="18" />Keluar</button>
      </div>
    </div>
  </header>
</template>
