package com.GraduationProject.GraduationProject.service;

import com.GraduationProject.GraduationProject.dto.ProductDTO;
import com.GraduationProject.GraduationProject.dto.RecycleDTO;
import com.GraduationProject.GraduationProject.entity.Product;
import com.GraduationProject.GraduationProject.entity.Recycle;
import com.GraduationProject.GraduationProject.entity.Usr;
import com.GraduationProject.GraduationProject.repo.RecycleRepo;
import com.GraduationProject.GraduationProject.repo.UsrRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecycleService {

    @Autowired
    private RecycleRepo recycleRepo;

    @Autowired
    private UsrRepo usrRepo;

    @Autowired
    private ProductService productService;

    @Transactional(readOnly = true)
    public List<RecycleDTO> getRecycleSummariesByUserId(Integer userId) {
        Usr user = usrRepo.findById(userId).orElse(null);

        if (user == null) {
            return null;
        }

        List<Recycle> recycles = recycleRepo.findByUsr(user);

        return recycles.stream()
                .map(this::convertToSummaryDto)
                .collect(Collectors.toList());
    }

    private RecycleDTO convertToSummaryDto(Recycle recycle) {
        RecycleDTO dto = new RecycleDTO();
        dto.setId(recycle.getId());
        dto.setRecycleName(recycle.getRecycleName());

        List<Product> products = productService.getProductsEntitiesByRecycleId(recycle.getId());
        double totalEffect = 0.0;
        List<ProductDTO> productDtos = new ArrayList<>();

        for (Product product : products) {
            ProductDTO productDto = new ProductDTO();
            productDto.setProductName(product.getFkproductType().getProductName());
            productDto.setCount(product.getCount());
            productDto.setUnitEffect(product.getFkproductType().getEffect());
            productDtos.add(productDto);

            totalEffect += product.getFkproductType().getEffect() * product.getCount();
        }

        dto.setProducts(productDtos);
        dto.setTotalContribution(totalEffect);

        return dto;
    }
}