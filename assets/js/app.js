/* ============================================================
   Control de Mortalidad Avícola — lógica de la aplicación
   Proyecto formativo SENA · ADSO
   ============================================================ */

const PENS = [
  { id: 1, name: "Galpón 1", capacity: 28000 },
  { id: 2, name: "Galpón 2", capacity: 28000 },
  { id: 3, name: "Galpón 3", capacity: 28000 },
  { id: 4, name: "Galpón 4", capacity: 28000 },
  { id: 5, name: "Galpón 5", capacity: 28000 },
  { id: 6, name: "Galpón 6", capacity: 28000 },
  { id: 7, name: "Galpón 7", capacity: 28000 },
  { id: 8, name: "Galpón 8", capacity: 28000 },
];

const STORAGE_KEY = "mortalidad_registros";

function getRecords() {
  try {
    return JSON.parse(localStorage.getItem(STORAGE_KEY)) || [];
  } catch (e) {
    return [];
  }
}

function saveRecord(record) {
  const records = getRecords();
  records.push(record);
  localStorage.setItem(STORAGE_KEY, JSON.stringify(records));
}

function todayISO() {
  return new Date().toISOString().slice(0, 10);
}

function formatDate(iso) {
  const [y, m, d] = iso.split("-");
  return `${d}/${m}/${y}`;
}

function penHasRecordToday(penId) {
  const today = todayISO();
  return getRecords().some((r) => r.penId === penId && r.date === today);
}

/* ---------- Sesión simple (demo) ---------- */
function requireSession() {
  if (!sessionStorage.getItem("mortalidad_user")) {
    window.location.href = "index.html";
  }
}

function currentUserInitial() {
  const user = sessionStorage.getItem("mortalidad_user") || "U";
  return user.trim().charAt(0).toUpperCase() || "U";
}

/* ---------- Resaltar el ítem activo del menú ---------- */
function markActiveNav() {
  const current = window.location.pathname.split("/").pop();
  document.querySelectorAll(".nav-list a").forEach((a) => {
    if (a.getAttribute("href") === current) a.classList.add("active");
  });
}

document.addEventListener("DOMContentLoaded", markActiveNav);
