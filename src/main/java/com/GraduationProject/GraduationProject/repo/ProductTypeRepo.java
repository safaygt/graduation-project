package com.GraduationProject.GraduationProject.repo;

import com.GraduationProject.GraduationProject.entity.ProductType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductTypeRepo extends JpaRepository<ProductType, Integer> {
    ProductType findByProductName(String productName);
}