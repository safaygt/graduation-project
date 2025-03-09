package com.GraduationProject.GraduationProject.repo;

import com.GraduationProject.GraduationProject.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepo extends JpaRepository<Product, Integer> {

}
