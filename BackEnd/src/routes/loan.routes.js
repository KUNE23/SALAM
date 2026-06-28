const router = require("express").Router();
const controller = require("../controllers/loan.controller");
const auth = require("../middlewares/auth.middleware");
const allowRoles = require("../middlewares/role.middleware");
const validate = require("../middlewares/validate.middleware");
const schema = require("../validations/loan.validation");

const staff = allowRoles("SUPER_ADMIN", "ADMIN", "SARPRAS");

router.get("/", auth, validate(schema.list), controller.list);
router.get("/pending", auth, staff, controller.pending);
router.get("/my", auth, allowRoles("SISWA"), validate(schema.list), controller.mine);
router.post("/", auth, allowRoles("SISWA"), validate(schema.create), controller.create);
router.patch("/:id/approve", auth, staff, validate(schema.action), controller.approve);
router.patch("/:id/reject", auth, staff, validate(schema.reject), controller.reject);
router.patch("/:id/return", auth, validate(schema.action), controller.returnLoan);

module.exports = router;
