package com.GraduationProject.GraduationProject.service;

import com.GraduationProject.GraduationProject.entity.Product;
import com.GraduationProject.GraduationProject.repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepo productRepo;

    public List<Product> getProductsEntitiesByRecycleId(Integer recycleId) {
        return productRepo.findByFkRecycle_Id(recycleId);
    }

}