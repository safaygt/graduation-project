package com.GraduationProject.GraduationProject.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/about")
public class AboutController {

    @GetMapping
    public ResponseEntity<String> about(){

        return ResponseEntity.ok("Getting about page");

    }

}
