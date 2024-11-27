const express = require("express");
const mongoose = require("mongoose");
const bodyParser = require("body-parser");
const OrderRoute = require("./route/OrderRoute");
const { Eureka } = require('eureka-js-client');

const app = express();

app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));

app.use("/order-payment-service/api/v1", OrderRoute);

// Eureka client configuration
const client = new Eureka({
  instance: {
      app: 'order-payment-service',
      hostName: 'localhost',
      ipAddr: '127.0.0.1',
      port: { '$': 3001, '@enabled': true },
      vipAddress: 'order-payment-service',
      dataCenterInfo: {
          '@class': 'com.netflix.appinfo.InstanceInfo$DefaultDataCenterInfo',
          name: 'MyOwn',
      },
  },
  eureka: {
      host: 'localhost',
      port: 8761,
      servicePath: '/eureka/apps/',
  },
});


mongoose.connect("mongodb://localhost:27017/ecommerce", {
  useNewUrlParser: true,
  useUnifiedTopology: true,
})
.then(() => console.log('MongoDB connected'))
    .catch(error => console.log('MongoDB connection error:', error));

// Eureka client start
client.start((error) => {
  if (error) {
      console.log('Eureka registration failed:', error);
  } else {
      console.log('Eureka registration successful!');
  }
});


app.listen(3000, () => {
  console.log("Server is running on port 3000");
  })