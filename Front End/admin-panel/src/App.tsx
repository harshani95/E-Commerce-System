import "./App.css";
import {
  createBrowserRouter,
  redirect,
  RouterProvider,
} from "react-router-dom";
import MainDrawer from "./components/MainDrawer";
import CustomerTable from "./components/customer/CustomerTable";
import ProductPage from "./components/product/ProductPage";
import OrderTable from "./components/order/OrderTable";
import LoginPage from "./components/LoginPage";

const router = createBrowserRouter([
  {
    path: "/",
    element: <MainDrawer />,
    children: [
      {
        index: true,
        loader: async () => redirect("/customer"),
      },
      {
        path: "customer",
        element: <CustomerTable />,
      },
      {
        path: "product",
        element: <ProductPage />,
      },
      {
        path: "order",
        element: <OrderTable />,
      },
    ],
  },
  {
    path: "/login",
    element: <LoginPage />,
  },
]);

function App() {
  return (
    <>
      <RouterProvider router={router} />
    </>
  );
}

export default App;
