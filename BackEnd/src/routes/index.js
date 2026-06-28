const router = require("express").Router();
const { success } = require("../utils/response");

router.get("/", (req, res) => success(res, "SALAM API siap digunakan"));

router.use("/auth", require("./auth.routes"));
router.use("/dashboard", require("./dashboard.routes"));
router.use("/students", require("./student.routes"));
router.use("/laptops", require("./laptop.routes"));
router.use("/loans", require("./loan.routes"));
router.use("/notifications", require("./notification.routes"));
router.use("/settings", require("./setting.routes"));
router.use("/laptop-categories", require("./laptopCategory.routes"));
router.use("/profile", require("./profile.routes"));

module.exports = router;
