package com.ecom.product_service_api.service;

import com.ecom.product_service_api.dto.request.RequestProductDto;
import com.ecom.product_service_api.dto.response.ResponseProductDto;
import com.ecom.product_service_api.dto.response.paginate.ResponseProductPaginate;
import org.springframework.web.multipart.MultipartFile;

public interface ProductService {
    public void createProduct(RequestProductDto productDto, MultipartFile file);
    public void updateProduct(RequestProductDto productDto , String productId);
    public void deleteProduct(String productId);
    public ResponseProductDto findProductById(String productId);
    public ResponseProductPaginate searchAllProducts(String searchText, int page, int size );
    public void updateImage(MultipartFile file, String imageId);
    public void deleteImage(String imageId);
}
