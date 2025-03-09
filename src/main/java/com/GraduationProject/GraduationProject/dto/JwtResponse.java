package com.GraduationProject.GraduationProject.dto;

import lombok.Data;
import org.springframework.http.HttpStatus;
@Data
public class JwtResponse {
    private String token;
    private HttpStatus responseCode;
}
