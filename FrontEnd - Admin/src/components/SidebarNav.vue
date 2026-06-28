<script setup>
import { CircleHelp, Laptop, LayoutDashboard, LogOut, PackageCheck, Settings, Users } from "lucide-vue-next";
import LogoMark from "./LogoMark.vue";

defineProps({
  page: { type: String, required: true }
});

defineEmits(["navigate", "logout"]);

const menus = [
  { key: "dashboard", label: "Dashboard", icon: LayoutDashboard },
  { key: "inventory", label: "inventasris", icon: Laptop },
  { key: "loans", label: "Loan Requests", icon: PackageCheck },
  { key: "users", label: "Users", icon: Users },
  { key: "settings", label: "Settings", icon: Settings }
];
</script>

<template>
  <aside class="sidebar">
    <LogoMark />
    <button v-for="item in menus" :key="item.key" :class="{ active: page === item.key }" @click="$emit('navigate', item.key)">
      <component :is="item.icon" :size="22" />
      {{ item.label }}
    </button>
    <div class="sidebar-bottom">
      <button><CircleHelp :size="21" />Support</button>
      <button @click="$emit('logout')"><LogOut :size="21" />Logout</button>
    </div>
  </aside>
</template>
