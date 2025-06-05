package com.GraduationProject.GraduationProject.config;


import com.GraduationProject.GraduationProject.entity.ProductType;
import com.GraduationProject.GraduationProject.repo.ProductTypeRepo;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

@Component
public class ProductTypeInitializer {

    private final ProductTypeRepo productTypeRepo;

    public ProductTypeInitializer(ProductTypeRepo productTypeRepo) {
        this.productTypeRepo = productTypeRepo;
    }

    @PostConstruct
    public void init() {
        addProductTypeIfNotExists("metal", 0.0023);
        addProductTypeIfNotExists("plastic", 0.0009);
        addProductTypeIfNotExists("paper", 0.0015);
        addProductTypeIfNotExists("glass", 0.0018);
    }

    private void addProductTypeIfNotExists(String name, double effect) {
        if (productTypeRepo.findByProductName(name) == null) {
            ProductType pt = new ProductType();
            pt.setProductName(name);
            pt.setEffect(effect);
            productTypeRepo.save(pt);
        }
    }
}


