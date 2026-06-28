<script setup>
import SidebarNav from "./SidebarNav.vue";
import TopBar from "./TopBar.vue";

defineProps({
  page: { type: String, required: true },
  user: { type: Object, required: true }
});

defineEmits(["navigate", "logout"]);
</script>

<template>
  <section class="admin-shell">
    <SidebarNav :page="page" @navigate="$emit('navigate', $event)" @logout="$emit('logout')" />
    <main>
      <TopBar :user="user" @logout="$emit('logout')" @settings="$emit('navigate', 'settings')" />
      <slot />
    </main>
  </section>
</template>
