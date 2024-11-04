const express = require("express");
const mongoose = require("mongoose");
const bodyParser = require("body-parser");
const OrderRoute = require("./route/OrderRoute");

const app = express();

app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));

app.use("/order", OrderRoute);

mongoose.connect("mongodb://localhost:27017/ecommerce", {
  useNewUrlParser: true,
  useUnifiedTopology: true,
})
.then(() => console.log('MongoDB connected'))
    .catch(error => console.log('MongoDB connection error:', error));


app.listen(3000, () => {
  console.log("Server is running on port 3000");
  })