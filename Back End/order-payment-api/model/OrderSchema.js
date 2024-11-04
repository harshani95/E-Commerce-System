const mongoose = require("mongoose");

const OrderSchema = new mongoose.Schema({
    orderDate: {
        type: Date,
        required: true,
      },

    orderCost: {
    type: Number,
    required: true,
  },

  customer:{
    type: Object,
    required: true,
  },

  products:{
    type: Object,
    required: true,
  },
  orderPayment: {
    type: Object,
    required: true,
  },
  
    orderStatus: {
    type: String,
    required: true,
  },
    
});

module.exports = mongoose.model("Order", OrderSchema); // "Order" is the name of the collection