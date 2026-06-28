const { z } = require("zod");

const login = z.object({
  body: z.object({
    email: z.string().email(),
    password: z.string().min(1)
  }),
  query: z.object({}).passthrough(),
  params: z.object({})
});

const refresh = z.object({
  body: z.object({
    refreshToken: z.string().min(20)
  }),
  query: z.object({}).passthrough(),
  params: z.object({})
});

const register = z.object({
  body: z.object({
    name: z.string().min(2),
    email: z.string().email(),
    password: z.string().min(6),
    role: z.enum(["SISWA"]).default("SISWA")
  }),
  query: z.object({}).passthrough(),
  params: z.object({})
});

module.exports = { login, refresh, register };
