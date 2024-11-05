package com.ecom.product_service_api.repo;

import com.ecom.product_service_api.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepo extends JpaRepository<Product, String> {
}
