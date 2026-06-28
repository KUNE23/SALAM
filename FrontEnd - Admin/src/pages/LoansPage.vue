<script setup>
import { Check, X } from "lucide-vue-next";
import PageHeader from "../components/PageHeader.vue";

defineProps({
  loans: { type: Array, default: () => [] }
});

const emit = defineEmits(["approve", "reject"]);

function initials(name = "AP") {
  return name.split(" ").map((part) => part[0]).join("").slice(0, 2).toUpperCase();
}

function formatDate(value) {
  if (!value) return "-";
  return new Intl.DateTimeFormat("id-ID", { day: "2-digit", month: "short", year: "numeric" }).format(new Date(value));
}
</script>

<template>
  <section class="content">
    <PageHeader title="Pengajuan Peminjaman" subtitle="Berikut daftar pengajuan peminjaman." />
    <div class="loan-grid">
      <article v-for="loan in loans" :key="loan.id" class="loan-card">
        <div class="loan-person"><span>{{ initials(loan.user?.name) }}</span><div><b>{{ loan.user?.name || 'Siswa' }}</b><small>{{ loan.user?.emailOrPhone || '-' }}</small></div><em>{{ loan.status === 'PENDING' ? 'Menunggu' : loan.status }}</em></div>
        <div class="loan-meta"><div><small>MULAI</small><b>{{ formatDate(loan.borrowedAt) }}</b></div><div><small>SELESAI</small><b>{{ formatDate(loan.returnDeadline) }}</b></div><div><small>DURASI</small><b>3 hari</b></div></div>
        <div v-if="loan.status === 'REJECTED'" class="loan-result rejected">x Sudah Ditolak</div>
        <div v-else-if="loan.status === 'APPROVED' || loan.status === 'BORROWED'" class="loan-result approved">Sudah Disetujui</div>
        <div v-else-if="loan.status === 'RETURNED'" class="loan-result approved">Sudah Dikembalikan</div>
        <div v-else class="loan-actions"><button class="approve" @click="emit('approve', loan.id)"><Check :size="14" />Setujui</button><button class="reject" @click="emit('reject', loan.id)"><X :size="14" />Tolak</button></div>
      </article>
    </div>
  </section>
</template>
