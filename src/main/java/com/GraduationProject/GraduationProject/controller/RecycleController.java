package com.GraduationProject.GraduationProject.controller;

import com.GraduationProject.GraduationProject.entity.Product;
import com.GraduationProject.GraduationProject.entity.Recycle;
import com.GraduationProject.GraduationProject.service.ProductService;
import com.GraduationProject.GraduationProject.service.RecycleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recycles")
public class RecycleController {

    @Autowired
    private RecycleService recycleService;

    @Autowired
    private ProductService productService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Recycle>> getRecyclesByUserId(@PathVariable Integer userId) {
        List<Recycle> recycles = recycleService.getRecyclesByUserId(userId);
        if (recycles != null) {
            return ResponseEntity.ok(recycles);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/recycle/{recycleId}/products")
    public ResponseEntity<List<Product>> getProductsByRecycleId(@PathVariable Integer recycleId) {
        List<Product> products = productService.getProductsByRecycleId(recycleId);
        if (products != null) {
            return ResponseEntity.ok(products);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}