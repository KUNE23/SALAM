<script setup>
import { Chart, BarController, BarElement, CategoryScale, LinearScale, Tooltip } from "chart.js";
import { Bell, ChevronDown, Download, Laptop, PackageCheck } from "lucide-vue-next";
import { computed, nextTick, onBeforeUnmount, onMounted, ref, watch } from "vue";
import PageHeader from "../components/PageHeader.vue";
import { openDashboardReport } from "../utils/report";

Chart.register(BarController, BarElement, CategoryScale, LinearScale, Tooltip);

const props = defineProps({
  summary: Object,
  laptops: { type: Array, default: () => [] },
  loans: { type: Array, default: () => [] },
  students: { type: Array, default: () => [] },
  setting: Object
});

const emit = defineEmits(["refresh"]);
const chartEl = ref(null);
let chart;

const borrowedCount = computed(() => props.laptops.filter((item) => item.status === "BORROWED").length);
const pendingCount = computed(() => props.loans.filter((item) => item.status === "PENDING").length);
const overdueCount = computed(() => props.loans.filter((item) => item.status === "OVERDUE").length);

const chartData = computed(() => {
  const counts = [0, 0, 0, 0, 0, 0, 0];
  props.loans.forEach((loan) => {
    const date = new Date(loan.requestDate || loan.borrowedAt || loan.createdAt || Date.now());
    const index = (date.getDay() + 6) % 7;
    counts[index] += 1;
  });
  return counts;
});

function drawChart() {
  if (!chartEl.value) return;
  if (chart) chart.destroy();
  chart = new Chart(chartEl.value, {
    type: "bar",
    data: {
      labels: ["MON", "TUE", "WED", "THU", "FRI", "SAT", "SUN"],
      datasets: [{
        data: chartData.value,
        backgroundColor: "#096ca4",
        borderRadius: 8,
        maxBarThickness: 42
      }]
    },
    options: {
      responsive: true,
      maintainAspectRatio: false,
      plugins: { legend: { display: false }, tooltip: { enabled: true } },
      scales: {
        x: { grid: { display: false }, ticks: { color: "#657895", font: { weight: "700" } } },
        y: { beginAtZero: true, grid: { color: "#eef2f7" }, ticks: { precision: 0, color: "#657895" } }
      }
    }
  });
}

function exportReport() {
  openDashboardReport({
    summary: props.summary,
    laptops: props.laptops,
    loans: props.loans,
    students: props.students,
    setting: props.setting
  });
}

onMounted(() => nextTick(drawChart));
watch(chartData, () => nextTick(drawChart), { deep: true });
onBeforeUnmount(() => chart?.destroy());
</script>

<template>
  <section class="content">
    <PageHeader title="Ringkasan sistem" subtitle="Selamat datang kembali, berikut kondisi saat ini">
      <div class="head-actions">
        <button @click="exportReport"><Download :size="16" />Export Report</button>
        <button class="blue" @click="emit('refresh')">Refresh Data</button>
      </div>
    </PageHeader>

    <div class="stat-grid">
      <article class="stat-card"><span class="stat-icon blue"><Laptop /></span><span class="stat-hint blue">+ 2%</span><b>TOTAL LAPTOP</b><strong>{{ summary?.totalLaptops ?? laptops.length }}</strong></article>
      <article class="stat-card"><span class="stat-icon green"><PackageCheck /></span><span class="stat-hint green">82% Active</span><b>LAPTOP DIPINJAM</b><strong>{{ summary?.borrowedLaptops ?? borrowedCount }}</strong></article>
      <article class="stat-card"><span class="stat-icon orange"><PackageCheck /></span><span class="stat-hint orange">Priority</span><b>PERMINTAAN MENUNGGU</b><strong>{{ pendingCount }}</strong></article>
      <article class="stat-card"><span class="stat-icon red"><Bell /></span><span class="stat-hint red">Urgent</span><b>PENEMBALIAN TERLAMBAT</b><strong>{{ overdueCount }}</strong></article>
    </div>

    <div class="dashboard-row">
      <section class="chart-card">
        <div class="card-top">
          <div><b>Aktivitas peminjaman</b><p>Total peminjam laptop yang diproses selama 7 hari</p></div>
          <button>7 hari terakhir <ChevronDown :size="15" /></button>
        </div>
        <div class="chart-wrap"><canvas ref="chartEl"></canvas></div>
      </section>
      <aside class="maintenance-card">
        <h2>Pemeliharaan cepat</h2>
        <p>Ada 5 laptop yang dijadwalkan untuk pemeriksaan kesehatan baterai</p>
        <button>View Schedule</button>
      </aside>
    </div>
  </section>
</template>
