<script setup>
import { Plus } from "lucide-vue-next";
import PageHeader from "../components/PageHeader.vue";

defineProps({
  laptops: { type: Array, default: () => [] }
});

defineEmits(["add"]);
</script>

<template>
  <section class="content">
    <PageHeader title="Daftar Laptop" subtitle="Berikut daftar laptop yang tersedia.">
      <button class="big-blue add-btn" @click="$emit('add')"><Plus :size="18" />Tambah Laptop</button>
    </PageHeader>
    <div class="laptop-grid">
      <article v-for="item in laptops" :key="item.id" class="laptop-card">
        <div class="laptop-art"><div class="screen"></div><div class="keyboard"></div></div>
        <h3>{{ item.name || `${item.brand || 'Lenovo Thinkpad'} ${item.series || ''}` }}</h3>
        <p>{{ item.ram || item.code }}</p>
      </article>
    </div>
  </section>
</template>
