package com.GraduationProject.GraduationProject.controller;

import com.GraduationProject.GraduationProject.service.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api")
public class MainController {

    @Autowired
    private MainService mainService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            String prediction = mainService.predictAndSave(file);
            return ResponseEntity.ok(prediction);
        } catch (IOException e) {
            return ResponseEntity.badRequest().body("Dosya işlenirken hata oluştu.");
        }
    }
}