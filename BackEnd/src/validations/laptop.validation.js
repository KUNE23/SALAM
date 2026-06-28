const { z } = require("zod");

const status = z.enum(["AVAILABLE", "BORROWED", "MAINTENANCE", "LOST"]);
const idParams = z.object({ id: z.string().cuid() });

const list = z.object({
  body: z.object({}).passthrough(),
  query: z.object({
    search: z.string().optional(),
    status: status.optional(),
    page: z.coerce.number().optional(),
    limit: z.coerce.number().optional()
  }),
  params: z.object({})
});

const create = z.object({
  body: z.object({
    brand: z.string().min(2),
    series: z.string().min(1),
    code: z.string().min(3),
    ram: z.string().optional(),
    storage: z.string().optional(),
    completeness: z.string().optional()
  }),
  query: z.object({}).passthrough(),
  params: z.object({})
});

const update = z.object({
  body: create.shape.body.partial(),
  query: z.object({}).passthrough(),
  params: idParams
});

const changeStatus = z.object({
  body: z.object({ status }),
  query: z.object({}).passthrough(),
  params: idParams
});

const detail = z.object({
  body: z.object({}).passthrough(),
  query: z.object({}).passthrough(),
  params: idParams
});

module.exports = { list, create, update, changeStatus, detail };
