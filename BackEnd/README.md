# SALAM BackEnd

REST API untuk SALAM - Sistem Aplikasi Laptop Anak Madani.

## Menjalankan Project

```bash
npm install
copy .env.example .env
```

Isi `DB_URL` PostgreSQL lokal di `.env`, lalu jalankan:

```bash
npx prisma migrate dev
npx prisma db seed
npm run dev
```

Server default berjalan di `http://localhost:5000`.

## Akun Seed

- Admin: `admin@absenku.com` / `admin123`
- Siswa: `yoganugraha@gmail.com` / `siswa123`

## Endpoint Utama

- `POST /api/auth/login`
- `POST /api/auth/refresh`
- `POST /api/auth/logout`
- `GET /api/auth/me`
- `GET /api/dashboard/summary`
- `GET /api/students`
- `GET /api/laptops`
- `GET /api/loans`
- `GET /api/notifications`
- `GET /api/settings`
- `GET /api/profile`

Semua endpoint selain login dan refresh memakai `Authorization: Bearer <accessToken>`.
