import { styled } from "@mui/material/styles";
import Table from "@mui/material/Table";
import TableBody from "@mui/material/TableBody";
import TableCell, { tableCellClasses } from "@mui/material/TableCell";
import TableContainer from "@mui/material/TableContainer";
import TableHead from "@mui/material/TableHead";
import TableRow from "@mui/material/TableRow";
import Paper from "@mui/material/Paper";
import { Product } from "./ProductPage";
import { Button } from "@mui/material";
import DeleteIcon from "@mui/icons-material/Delete";
import EditIcon from "@mui/icons-material/Edit";
import { useState } from "react";
import DeleteConfirmationDialog from "../common/DeleteConfirmationDialog";

const StyledTableCell = styled(TableCell)(({ theme }) => ({
  [`&.${tableCellClasses.head}`]: {
    backgroundColor: theme.palette.common.black,
    color: theme.palette.common.white,
  },
  [`&.${tableCellClasses.body}`]: {
    fontSize: 14,
  },
}));

const StyledTableRow = styled(TableRow)(({ theme }) => ({
  "&:nth-of-type(odd)": {
    backgroundColor: theme.palette.action.hover,
  },
  // hide last border
  "&:last-child td, &:last-child th": {
    border: 0,
  },
}));

function createData(
  productId: number,
  description: string,
  unitPrice: number,
  qty: number
) {
  return { productId, description, unitPrice, qty };
}

const rows = [
  createData(159, "Frozen yoghurt", 6.0, 24),
  createData(237, "Ice cream sandwich", 9.0, 37),
  createData(262, "Eclair", 16.0, 24),
];

export default function ProductTable({
  onRowClick,
}: {
  onRowClick: (product: Product) => void;
}) {
  const [open, setOpen] = useState(false);
  const [itemName, setItemName] = useState("");

  return (
    <>
      <TableContainer component={Paper}>
        <Table sx={{ minWidth: 700 }} aria-label="customized table">
          <TableHead>
            <TableRow>
              <StyledTableCell>Product Id </StyledTableCell>
              <StyledTableCell align="right">Description</StyledTableCell>
              <StyledTableCell align="right">Unit Price</StyledTableCell>
              <StyledTableCell align="right">QTY</StyledTableCell>
              <StyledTableCell align="center">Actions</StyledTableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {rows.map((row) => (
              <StyledTableRow key={row.productId}>
                <StyledTableCell component="th" scope="row">
                  {row.productId}
                </StyledTableCell>
                <StyledTableCell align="right">
                  {row.description}
                </StyledTableCell>
                <StyledTableCell align="right">{row.unitPrice}</StyledTableCell>
                <StyledTableCell align="right">{row.qty}</StyledTableCell>
                <StyledTableCell
                  align="center"
                  sx={{
                    display: "flex",
                    justifyContent: "center",
                    gap: "10px",
                  }}
                >
                  <Button
                    variant="outlined"
                    startIcon={<EditIcon />}
                    onClick={() => {
                      onRowClick({
                        id: 1,
                        name: "Product 1",
                        description: "Product 1 Description",
                        price: 1000,
                        quantity: 10,
                      });
                    }}
                  >
                    Edit
                  </Button>
                  <Button
                    variant="outlined"
                    startIcon={<DeleteIcon />}
                    onClick={() => {
                      setItemName("Product 1");
                      setOpen(true);
                    }}
                  >
                    Delete
                  </Button>
                </StyledTableCell>
              </StyledTableRow>
            ))}
          </TableBody>
        </Table>
      </TableContainer>
      <DeleteConfirmationDialog
        open={open}
        handleClose={() => {
          setOpen(false);
        }}
        itemName={itemName}
      />
    </>
  );
}
