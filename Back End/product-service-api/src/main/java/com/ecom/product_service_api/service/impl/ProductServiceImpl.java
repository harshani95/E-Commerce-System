package com.ecom.product_service_api.service.impl;

import com.ecom.product_service_api.dto.request.RequestProductDto;
import com.ecom.product_service_api.dto.response.ResponseProductDto;
import com.ecom.product_service_api.dto.response.paginate.ResponseProductPaginate;
import com.ecom.product_service_api.repo.ProductRepo;
import com.ecom.product_service_api.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepo productRepo;

    @Override
    public void createProduct(RequestProductDto productDto, MultipartFile file) {

    }

    @Override
    public void updateProduct(RequestProductDto productDto, String productId) {

    }

    @Override
    public void deleteProduct(String productId) {

    }

    @Override
    public ResponseProductDto findProductById(String productId) {
        return null;
    }

    @Override
    public ResponseProductPaginate searchAllProducts(String searchText, int page, int size) {
        return null;
    }

    @Override
    public void updateImage(MultipartFile file, String imageId) {

    }

    @Override
    public void deleteImage(String imageId) {

    }
}
