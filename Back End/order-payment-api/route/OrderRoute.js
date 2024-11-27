const express = require("express");
const router = express.Router();
const OrderController = require("../controller/OrderController");

router.post("/placeOrder", OrderController.placeOrer);
router.get("/loadAllOrders", OrderController.loadAllOrders);
router.put("/updateOrderState", OrderController.updateOrderState);
router.delete("/deleteOrderByAdmin", OrderController.deleteOrderByAdmin);

module.exports = router;