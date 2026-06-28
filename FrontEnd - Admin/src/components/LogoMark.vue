<script setup>
import salamIcon from "../assets/salam-icon.png";
import salamText from "../assets/salam-text.png";

defineProps({
  large: Boolean
});
</script>

<template>
  <div :class="large ? 'logo-large' : 'logo-compact'">
    <img :src="salamIcon" alt="SALAM" class="logo-image-icon">
    <img :src="salamText" alt="SALAM Sistem Aplikasi Laptop Anak Madani" class="logo-image-text">
  </div>
</template>
