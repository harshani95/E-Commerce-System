package com.ecom.product_service_api.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RequestProductDto {
    @NotBlank(message = "Description is mandatory")
    private String description;

    @NotBlank(message = "Unit Price is mandatory")
    private double unitPrice;

    @NotBlank(message = "Quantity is mandatory")
    private int qty;
}
