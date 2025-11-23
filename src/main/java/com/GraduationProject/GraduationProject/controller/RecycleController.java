package com.GraduationProject.GraduationProject.controller;

import com.GraduationProject.GraduationProject.dto.RecycleDTO;
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

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<RecycleDTO>> getRecyclesByUserId(@PathVariable Integer userId) {
        List<RecycleDTO> recycles = recycleService.getRecycleSummariesByUserId(userId);

        if (recycles != null && !recycles.isEmpty()) {
            return ResponseEntity.ok(recycles);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}