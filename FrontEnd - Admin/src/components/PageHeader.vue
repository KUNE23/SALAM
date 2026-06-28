<script setup>
defineProps({
  title: { type: String, required: true },
  subtitle: { type: String, default: "" }
});
</script>

<template>
  <div class="page-head">
    <div>
      <h1>{{ title }}</h1>
      <p>{{ subtitle }}</p>
    </div>
    <slot />
  </div>
</template>
