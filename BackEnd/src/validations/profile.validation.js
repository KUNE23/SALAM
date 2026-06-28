const { z } = require("zod");

const update = z.object({
  body: z.object({
    name: z.string().min(2).optional(),
    avatarUrl: z.string().url().optional(),
    nisn: z.string().min(3).optional(),
    className: z.string().optional(),
    major: z.string().optional(),
    employeeNumber: z.string().optional()
  }),
  query: z.object({}).passthrough(),
  params: z.object({})
});

const password = z.object({
  body: z.object({
    currentPassword: z.string().min(1),
    newPassword: z.string().min(6)
  }),
  query: z.object({}).passthrough(),
  params: z.object({})
});

module.exports = { update, password };
