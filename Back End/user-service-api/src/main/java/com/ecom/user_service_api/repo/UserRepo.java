package com.ecom.user_service_api.repo;

import com.ecom.user_service_api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, String> {
    Optional<User> findByEmail(String email);
}
