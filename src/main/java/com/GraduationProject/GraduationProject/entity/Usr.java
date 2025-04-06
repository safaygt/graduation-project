package com.GraduationProject.GraduationProject.entity;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="usr")
@Data
public class Usr {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;


}
