const router = require("express").Router();
const controller = require("../controllers/profile.controller");
const auth = require("../middlewares/auth.middleware");
const validate = require("../middlewares/validate.middleware");
const schema = require("../validations/profile.validation");

router.get("/", auth, controller.get);
router.put("/", auth, validate(schema.update), controller.update);
router.put("/password", auth, validate(schema.password), controller.password);

module.exports = router;
