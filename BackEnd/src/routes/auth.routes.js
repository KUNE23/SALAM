const router = require("express").Router();
const controller = require("../controllers/auth.controller");
const auth = require("../middlewares/auth.middleware");
const validate = require("../middlewares/validate.middleware");
const { loginLimiter } = require("../middlewares/security.middleware");
const schema = require("../validations/auth.validation");

router.post("/login", loginLimiter, validate(schema.login), controller.login);
router.post("/register", validate(schema.register), controller.register);
router.post("/refresh", validate(schema.refresh), controller.refresh);
router.post("/logout", auth, controller.logout);
router.get("/me", auth, controller.me);

module.exports = router;
