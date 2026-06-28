const { z } = require("zod");

const idParams = z.object({ id: z.string().cuid() });

const list = z.object({
  body: z.object({}).passthrough(),
  query: z.object({
    status: z.string().optional(),
    page: z.coerce.number().optional(),
    limit: z.coerce.number().optional()
  }),
  params: z.object({})
});

const create = z.object({
  body: z.object({ laptopCode: z.string().min(3) }),
  query: z.object({}).passthrough(),
  params: z.object({})
});

const reject = z.object({
  body: z.object({ reason: z.string().min(2).optional() }),
  query: z.object({}).passthrough(),
  params: idParams
});

const action = z.object({
  body: z.object({}).passthrough(),
  query: z.object({}).passthrough(),
  params: idParams
});

module.exports = { list, create, reject, action };
