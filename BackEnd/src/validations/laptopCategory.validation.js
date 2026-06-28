const { z } = require("zod");

const idParams = z.object({ id: z.string().cuid() });
const status = z.enum(["ACTIVE", "INACTIVE"]);

const create = z.object({
  body: z.object({
    name: z.string().min(2),
    description: z.string().optional(),
    unitCount: z.coerce.number().int().min(0).default(0),
    status: status.default("ACTIVE")
  }),
  query: z.object({}).passthrough(),
  params: z.object({})
});

const update = z.object({
  body: create.shape.body.partial(),
  query: z.object({}).passthrough(),
  params: idParams
});

const detail = z.object({
  body: z.object({}).passthrough(),
  query: z.object({}).passthrough(),
  params: idParams
});

module.exports = { create, update, detail };
