package com.ecom.product_service_api.controller;

import com.ecom.product_service_api.dto.request.RequestProductDto;
import com.ecom.product_service_api.dto.response.ResponseProductDto;
import com.ecom.product_service_api.dto.response.paginate.ResponseProductPaginate;
import com.ecom.product_service_api.service.ProductService;
import com.ecom.product_service_api.util.StandardResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/products-service/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping("/save")
    public ResponseEntity<StandardResponse> createProduct(
            @RequestParam("data") String data,
            @RequestParam("image") MultipartFile image
    ) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        RequestProductDto productDto = objectMapper.readValue(data,RequestProductDto.class);
        productService.createProduct(productDto,image);
        return new ResponseEntity<>(
                new StandardResponse(201,null,"product saved.."),
                HttpStatus.CREATED
        );
    }

}
