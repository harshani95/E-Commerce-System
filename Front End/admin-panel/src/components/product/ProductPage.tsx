import { Button, Stack } from "@mui/material";
import ProductTable from "./ProductTable";
import AddOrEditProductDialog from "./AddOrEditProductDialog";
import React from "react";

export interface Product {
  id?: number;
  name: string;
  description: string;
  price: number;
  quantity: number;
}

const ProductPage = () => {
  const [open, setOpen] = React.useState(false);
  const [isEditing, setIsEditing] = React.useState(false);
  const [selectedProduct, setSelectedProduct] = React.useState<Product | null>(
    null
  );
  const handleClickOpen = () => {
    setOpen(true);
  };
  const handleClose = () => {
    setOpen(false);
    setSelectedProduct(null);
    setIsEditing(false);
  };

  return (
    <>
      <Stack spacing={2} direction="row" justifyContent={"space-between"}>
        <h1>Product Page</h1>
        <div>
          <Button variant="contained" color="primary" onClick={handleClickOpen}>
            + Add Product
          </Button>
        </div>
      </Stack>
      <br />
      <ProductTable
        onRowClick={(product: Product) => {
          setSelectedProduct(product);
          setIsEditing(true);
          setOpen(true);
        }}
      />
      <AddOrEditProductDialog
        open={open}
        handleClose={handleClose}
        isEditing={isEditing}
        selectedProduct={selectedProduct}
      />
    </>
  );
};

export default ProductPage;
