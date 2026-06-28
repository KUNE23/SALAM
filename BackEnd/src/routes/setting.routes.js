const router = require("express").Router();
const controller = require("../controllers/setting.controller");
const auth = require("../middlewares/auth.middleware");
const allowRoles = require("../middlewares/role.middleware");
const validate = require("../middlewares/validate.middleware");
const schema = require("../validations/setting.validation");

router.get("/", auth, controller.get);
router.put("/", auth, allowRoles("SUPER_ADMIN", "ADMIN"), validate(schema.update), controller.update);

module.exports = router;
