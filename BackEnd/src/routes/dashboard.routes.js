const router = require("express").Router();
const controller = require("../controllers/dashboard.controller");
const auth = require("../middlewares/auth.middleware");
const allowRoles = require("../middlewares/role.middleware");

router.get("/summary", auth, allowRoles("SUPER_ADMIN", "ADMIN", "SARPRAS"), controller.summary);

module.exports = router;
