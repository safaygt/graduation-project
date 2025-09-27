package com.GraduationProject.GraduationProject.dto;

import lombok.Data;
import java.util.List;

@Data
public class RecycleDTO {
    private Integer id;
    private String recycleName;
    private Double totalContribution;
    private List<ProductDTO> products;
    // private Integer userId;
}