package com.GraduationProject.GraduationProject.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "recycle")
@Data
public class Recycle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;

    @Column(nullable = false, unique = true)
    private String recycleName;


    @ManyToOne
    @JoinColumn(name="fkusr",nullable = false)
    private Usr usr;


}
