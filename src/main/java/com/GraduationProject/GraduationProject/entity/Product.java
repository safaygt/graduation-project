package com.GraduationProject.GraduationProject.entity;



import jakarta.persistence.*;
import lombok.Data;
@Entity
@Table(name="product")
@Data

public class Product {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;


    @ManyToOne
    @JoinColumn(name = "fkproductType", nullable = false)
    private ProductType fkproductType;

    @ManyToOne
    @JoinColumn(name="fkrecycle", nullable = false)
    private Recycle fkRecycle;

    @Column(nullable = false)
    private Integer count;
}
