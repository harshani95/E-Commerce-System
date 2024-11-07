package com.ecom.product_service_api.controller;

import com.ecom.product_service_api.dto.request.RequestProductDto;
import com.ecom.product_service_api.dto.response.ResponseProductDto;
import com.ecom.product_service_api.dto.response.paginate.ResponseProductPaginate;
import com.ecom.product_service_api.entity.Product;
import com.ecom.product_service_api.service.ProductService;
import com.ecom.product_service_api.util.StandardResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

    @PutMapping("/update/{productId}")
    public ResponseEntity<StandardResponse> updateProduct(
            @RequestBody RequestProductDto productDto , @PathVariable String productId){
               productService.updateProduct(productDto, productId);
               return new ResponseEntity<>(
                       new StandardResponse(201,null,"Product Updated.."),
                       HttpStatus.CREATED
               );
    }

    @DeleteMapping("/delete/productId")
    public ResponseEntity<StandardResponse> deleteProduct(@PathVariable String productId){
        productService.deleteProduct(productId);
        return new ResponseEntity<>(
                new StandardResponse(204,"null","Product Deleted.."),
                HttpStatus.NO_CONTENT
        );
    }

    @GetMapping("/find-by-id/{productId}")
    public ResponseEntity<StandardResponse> findProductById(@PathVariable String productId){
         ResponseProductDto responseProductDto = productService.findProductById(productId);
        return new ResponseEntity<>(
                new StandardResponse(200,responseProductDto,"Product Data.."),
                HttpStatus.OK
        );
    }

    @GetMapping("/search-all-products")
    public ResponseEntity<StandardResponse> searchAllProducts(
            @RequestParam String searchText, @RequestParam int page, @RequestParam int size ){
        ResponseProductPaginate responseProductPaginate= productService.searchAllProducts(searchText,page,size);
            return new ResponseEntity<>(
                    new StandardResponse(200,responseProductPaginate,"Product List.."),
                    HttpStatus.OK
            );
    }

    @PutMapping("/update-image/{imageId}")
    public ResponseEntity<StandardResponse> updateImage(
            @RequestParam("image") MultipartFile file,
            @PathVariable String imageId

    ) {
        productService.updateImage(file,imageId );
        return new ResponseEntity<>(
                new StandardResponse(201, null, "image updated.."),
                HttpStatus.CREATED
        );
    }

    @DeleteMapping("/delete-image-by-id/{imageId}")
    public ResponseEntity<StandardResponse> deleteImage(@PathVariable String imageId) {
        productService.deleteImage(imageId);
        return new ResponseEntity<>(
                new StandardResponse(204, null, "image deleted.."),
                HttpStatus.NO_CONTENT
        );
    }

}
