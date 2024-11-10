package com.ecom.product_service_api.repo;

import com.ecom.product_service_api.entity.Images;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepo extends JpaRepository<Images, String> {
}
