const { z } = require("zod");

const idParams = z.object({ id: z.string().cuid() });
const statusEnum = z.enum(["ACTIVE", "INACTIVE"]);

const list = z.object({
  body: z.object({}).passthrough(),
  query: z.object({
    search: z.string().optional(),
    className: z.string().optional(),
    status: statusEnum.optional(),
    page: z.coerce.number().optional(),
    limit: z.coerce.number().optional()
  }),
  params: z.object({})
});

const create = z.object({
  body: z.object({
    name: z.string().min(2),
    email: z.string().email(),
    password: z.string().min(6).optional(),
    nisn: z.string().min(3),
    className: z.string().min(1),
    major: z.string().optional(),
    status: statusEnum.optional()
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
  body: z.object({ status: statusEnum }),
  query: z.object({}).passthrough(),
  params: idParams
});

module.exports = { list, create, update, changeStatus };
