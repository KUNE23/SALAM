const router = require("express").Router();
const controller = require("../controllers/notification.controller");
const auth = require("../middlewares/auth.middleware");

router.get("/", auth, controller.list);
router.patch("/read-all", auth, controller.readAll);
router.patch("/:id/read", auth, controller.read);

module.exports = router;
