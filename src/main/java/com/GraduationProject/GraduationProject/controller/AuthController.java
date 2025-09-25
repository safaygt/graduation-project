package com.GraduationProject.GraduationProject.controller;

import com.GraduationProject.GraduationProject.dto.JwtResponse;
import com.GraduationProject.GraduationProject.dto.UsrDTO;
import com.GraduationProject.GraduationProject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    private static final String PASSWORD_REGEX = "^(?=.*[A-Z])(?=.*\\d).{8,}$";
    private static final Pattern passwordPattern = Pattern.compile(PASSWORD_REGEX);

    @Autowired
    public AuthController(@Lazy UserService userService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UsrDTO usrDTO) {
        Matcher matcher = passwordPattern.matcher(usrDTO.getPassword());
        if (!matcher.matches()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Şifre en az 8 karakter olmalı, bir büyük harf ve bir rakam içermelidir.");
        }

        if (!usrDTO.isConsentChecked()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Kayıt olmak için açık rıza metnini onaylamanız gerekmektedir.");
        }

        if (userService.usernameExists(usrDTO.getUsername())) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Bu kullanıcı adı zaten kullanılıyor!");
        }

        userService.register(usrDTO);
        return ResponseEntity.ok("Kayıt başarılı.");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UsrDTO usrDTO) {
        JwtResponse jwtResponse = new JwtResponse();
        try {
            String token = userService.login(usrDTO, authenticationManager);
            jwtResponse.setToken(token);
            jwtResponse.setResponseCode(HttpStatus.OK);
            return ResponseEntity.ok(jwtResponse);
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Giriş hatalı");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There is an error!");
        }
    }
}