package com.ecom.product_service_api.service.impl;

import com.ecom.product_service_api.dto.CommonFileSavedBinaryDataDto;
import com.ecom.product_service_api.dto.request.RequestProductDto;
import com.ecom.product_service_api.dto.response.ResponseImage;
import com.ecom.product_service_api.dto.response.ResponseProductDto;
import com.ecom.product_service_api.dto.response.paginate.ResponseProductPaginate;
import com.ecom.product_service_api.entity.FileResource;
import com.ecom.product_service_api.entity.Images;
import com.ecom.product_service_api.entity.Product;
import com.ecom.product_service_api.exception.EntryNotFoundException;
import com.ecom.product_service_api.repo.ImageRepo;
import com.ecom.product_service_api.repo.ProductRepo;
import com.ecom.product_service_api.service.FileService;
import com.ecom.product_service_api.service.ProductService;
import com.ecom.product_service_api.util.FileDataExtractor;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepo productRepo;
    private final ImageRepo imageRepo;
    private final FileService fileService;
    private final FileDataExtractor fileDataExtractor;

    @Value("${bucketName}")
    private String bucketName;

    @Override
    public void createProduct(RequestProductDto productDto, MultipartFile file) {
        CommonFileSavedBinaryDataDto resource =null;
        try{
            resource = fileService.create(file,"abc/images/srilanka/",bucketName);
            Set<Images> images = new HashSet<>();
            images.add(
                    Images.builder()
                            .fileResource(
                                    new FileResource(
                                            resource.getHash(), resource.getFileName(),
                                            resource.getResourceUrl(), resource.getDirectory()
                                    )
                            )
                            .imageId(UUID.randomUUID().toString())
                            .build()
            );
            Product product = Product.builder()
                    .qty(productDto.getQty())
                    .description(productDto.getDescription())
                    .images(images)
                    .productId(UUID.randomUUID().toString())
                    .unitPrice(productDto.getUnitPrice())
                    .build();
            productRepo.save(product);
        } catch (IOException | SQLException e) {
            fileService.delete(fileDataExtractor.blobToString(resource.getDirectory()),
                    fileDataExtractor.blobToString(resource.getFileName()),bucketName);
            throw new RuntimeException(e);
        }
    }


    @Override
    public void updateProduct(RequestProductDto productDto, String productId) {
        Optional<Product> selectedProductData = productRepo.findById(productId);
        if (selectedProductData.isEmpty()) {
            throw new EntryNotFoundException("Product not found");
        }
        selectedProductData.get().setDescription(productDto.getDescription());
        selectedProductData.get().setQty(productDto.getQty());
        selectedProductData.get().setUnitPrice(productDto.getUnitPrice());
        productRepo.save(selectedProductData.get());
    }

    @Override
    public void deleteProduct(String productId) {
        Optional<Product> selectedProductData = productRepo.findById(productId);
        if (selectedProductData.isEmpty()) {
            throw new EntryNotFoundException("Product not found");
        }
        productRepo.deleteById(productId);
    }

    @Override
    public ResponseProductDto findProductById(String productId) {

        Optional<Product> selectedProductData = productRepo.findById(productId);
        if (selectedProductData.isEmpty()) {
            throw new EntryNotFoundException("Product not found");
        }
        return convert(selectedProductData.get());
    }
    ResponseProductDto convert(Product p) {
        if (p == null) {
            return null;
        }
        List<ResponseImage> images = new ArrayList<>();
        for (Images i : p.getImages()) {
            images.add(
                    new ResponseImage(i.getImageId(), fileDataExtractor.blobToString(i.getFileResource().getResourceUrl()))
            );
        }
        return ResponseProductDto.builder()
                .productId(p.getProductId())
                .unitPrice(p.getUnitPrice())
                .qty(p.getQty())
                .description(p.getDescription())
                .images(images)
                .build();
    }

    @Override
    public ResponseProductPaginate searchAllProducts(String searchText, int page, int size) {

        return ResponseProductPaginate.builder()
                .count(productRepo.searchCount(searchText))
                .productDtoList(
                        productRepo.search(searchText, PageRequest.of(page, size)).stream().map(this::convert).toList()
                )
                .build();

    }

    @Override
    public void updateImage(MultipartFile file, String imageId) {
        Optional<Images> selectedImageData = imageRepo.findById(imageId);
        if (selectedImageData.isEmpty()) {
            throw new EntryNotFoundException("image not found");
        }
        deleteImage(imageId);
        Optional<Product> selectedProductData = productRepo.findById(selectedImageData.get().getProduct().getProductId());
        if (selectedProductData.isEmpty()) {
            throw new EntryNotFoundException("product not found");
        }
        CommonFileSavedBinaryDataDto resource = null;
        try {
            resource = fileService.create(file, "abc/images/srilanka/", bucketName);
            imageRepo.save(Images.builder()
                    .fileResource(
                            new FileResource(
                                    resource.getHash(), resource.getFileName(),
                                    resource.getResourceUrl(), resource.getDirectory()
                            )
                    )
                    .imageId(UUID.randomUUID().toString())
                    .build());
        } catch (SQLException | IOException e) {
            fileService.delete(fileDataExtractor.blobToString(resource.getDirectory()),
                    fileDataExtractor.blobToString(resource.getFileName()), bucketName);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteImage(String imageId) {
        Optional<Images> selectedImageData = imageRepo.findById(imageId);
        if (selectedImageData.isEmpty()) {
            throw new EntryNotFoundException("image not found");
        }
        imageRepo.deleteById(imageId);
    }
}
