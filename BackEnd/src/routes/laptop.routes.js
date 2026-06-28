const router = require("express").Router();
const controller = require("../controllers/laptop.controller");
const auth = require("../middlewares/auth.middleware");
const allowRoles = require("../middlewares/role.middleware");
const validate = require("../middlewares/validate.middleware");
const upload = require("../middlewares/upload.middleware");
const schema = require("../validations/laptop.validation");

const staff = allowRoles("SUPER_ADMIN", "ADMIN", "SARPRAS");

router.get("/", auth, validate(schema.list), controller.list);
router.get("/available", auth, controller.available);
router.get("/:id", auth, validate(schema.detail), controller.detail);
router.post("/", auth, staff, upload.single("photo"), validate(schema.create), controller.create);
router.put("/:id", auth, staff, upload.single("photo"), validate(schema.update), controller.update);
router.delete("/:id", auth, staff, validate(schema.detail), controller.remove);
router.patch("/:id/status", auth, staff, validate(schema.changeStatus), controller.changeStatus);

module.exports = router;
