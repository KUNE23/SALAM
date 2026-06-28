const router = require("express").Router();
const controller = require("../controllers/student.controller");
const auth = require("../middlewares/auth.middleware");
const allowRoles = require("../middlewares/role.middleware");
const validate = require("../middlewares/validate.middleware");
const schema = require("../validations/student.validation");

router.get("/", auth, allowRoles("SUPER_ADMIN", "ADMIN", "SARPRAS"), validate(schema.list), controller.list);
router.post("/", auth, allowRoles("SUPER_ADMIN", "ADMIN"), validate(schema.create), controller.create);
router.put("/:id", auth, allowRoles("SUPER_ADMIN", "ADMIN"), validate(schema.update), controller.update);
router.delete("/:id", auth, allowRoles("SUPER_ADMIN", "ADMIN"), validate(schema.update), controller.remove);
router.patch("/:id/status", auth, allowRoles("SUPER_ADMIN", "ADMIN"), validate(schema.changeStatus), controller.changeStatus);

module.exports = router;
