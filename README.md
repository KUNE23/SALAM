# SALAM (Sistem Aplikasi Laptop Anak Madani)

SALAM merupakan sistem peminjaman laptop yang terdiri dari:

- Aplikasi Android untuk Siswa
- Website Admin
- REST API Backend
- Database PostgreSQL

---

# Tech Stack

## Mobile Application

- Kotlin
- Jetpack Compose
- Android Studio
- Retrofit
- OkHttp
- Room Database
- DataStore
- Material Design 3

## Admin Website

- Vue.js 3
- Vite
- Vue Router
- Axios
- Tailwind CSS

## Backend

- Node.js
- Express.js
- Prisma ORM
- JWT Authentication
- Bcrypt
- CORS
- Dotenv

## Database

- PostgreSQL

---

# Software yang Harus Diinstall

| Software | Versi Disarankan |
|-----------|------------------|
| Node.js | 20 LTS atau terbaru |
| PostgreSQL | 16+ |
| pgAdmin 4 | Terbaru |
| Android Studio | Meerkat / terbaru |
| JDK | 17 |
| Git | Terbaru |

---

# Struktur Folder

```
SALAM/
│
├── Backend/          # REST API Express.js
│
├── FrontEnd/         # Android App (Kotlin)
│
└── Admin/            # Website Admin (Vue.js)
```

---

# Cara Install Project

## 1. Clone Repository

```bash
git clone <repository-url>
cd SALAM
```

---

# 2. Setup Database

Buat database PostgreSQL

```
salam_db
```

Import:

- Schema Database
- Seeder Database

menggunakan pgAdmin 4.

---

# 3. Setup Backend

Masuk ke folder Backend

```bash
cd Backend
```

Install dependency

```bash
npm install
```

Buat file

```
.env
```

Contoh

```env
DATABASE_URL="postgresql://postgres:password@localhost:5432/salam_db"

JWT_SECRET=your-secret-key

PORT=5000
```

Generate Prisma

```bash
npx prisma generate
```

Push Schema

```bash
npx prisma db push
```

Jalankan Backend

```bash
npm run dev
```

Backend berjalan di

```
http://localhost:5000
```

---

# 4. Setup Website Admin

Masuk ke folder

```bash
cd Admin
```

Install dependency

```bash
npm install
```

Jalankan

```bash
npm run dev
```

Website berjalan pada

```
http://localhost:5173
```

Pastikan URL API mengarah ke

```
http://localhost:5000/api
```

---

# 5. Setup Android

Masuk ke folder

```bash
cd FrontEnd
```

Buka menggunakan Android Studio.

Pastikan menggunakan

- JDK 17
- Android SDK 35

Lalu lakukan

```
Sync Gradle
```

---

# 6. Konfigurasi API Android

Jika menggunakan Emulator

```
http://10.0.2.2:5000/api/
```

Jika menggunakan HP Fisik

```
http://IP_KOMPUTER:5000/api/
```

Contoh

```
http://192.168.1.10:5000/api/
```

---

# Menjalankan Project

## Backend

```bash
npm run dev
```

## Website Admin

```bash
npm run dev
```

## Android

Klik tombol **Run ▶** pada Android Studio menggunakan Emulator atau perangkat fisik.

---

# Port Default

| Service | Port |
|----------|------|
| Backend API | 5000 |
| Website Admin | 5173 |
| PostgreSQL | 5432 |

---

# Dependency Utama

## Backend

- Express.js
- Prisma ORM
- JWT
- Bcrypt
- CORS
- Dotenv

## Website Admin

- Vue.js 3
- Vue Router
- Axios
- Tailwind CSS
- Vite

## Android

- Jetpack Compose
- Retrofit
- OkHttp
- Room Database
- DataStore
- Navigation Compose

---

# Catatan

- PostgreSQL harus aktif sebelum menjalankan aplikasi.
- Jalankan Backend terlebih dahulu.
- Website Admin dan Android menggunakan REST API yang sama.
- Pastikan URL API sesuai dengan perangkat yang digunakan.
