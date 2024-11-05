package com.ecom.product_service_api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResponseProductDto {
    private  String productId;
    private String description;
    private double unitPrice;
    private int qty;
    private List<ResponseImage> images;
}
