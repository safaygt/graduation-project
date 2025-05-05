package com.GraduationProject.GraduationProject.repo;

import com.GraduationProject.GraduationProject.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepo extends JpaRepository<Product, Integer> {
    List<Product> findByFkRecycle_Id(Integer recycleId);
}