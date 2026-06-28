const router = require("express").Router();
const controller = require("../controllers/laptopCategory.controller");
const auth = require("../middlewares/auth.middleware");
const allowRoles = require("../middlewares/role.middleware");
const validate = require("../middlewares/validate.middleware");
const schema = require("../validations/laptopCategory.validation");

router.get("/", auth, controller.list);
router.post("/", auth, allowRoles("SUPER_ADMIN", "ADMIN"), validate(schema.create), controller.create);
router.put("/:id", auth, allowRoles("SUPER_ADMIN", "ADMIN"), validate(schema.update), controller.update);
router.delete("/:id", auth, allowRoles("SUPER_ADMIN", "ADMIN"), validate(schema.detail), controller.remove);

module.exports = router;
