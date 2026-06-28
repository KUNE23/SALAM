const { z } = require("zod");

const update = z.object({
  body: z.object({
    appName: z.string().min(2),
    openTime: z.string().regex(/^\d{2}:\d{2}$/),
    closeTime: z.string().regex(/^\d{2}:\d{2}$/),
    maxLoanDays: z.coerce.number().int().min(1).max(30)
  }),
  query: z.object({}).passthrough(),
  params: z.object({})
});

module.exports = { update };
