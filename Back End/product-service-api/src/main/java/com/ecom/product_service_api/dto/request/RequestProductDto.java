package com.ecom.product_service_api.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RequestProductDto {
    private String description;
    private double unitPrice;
    private int qty;
}
