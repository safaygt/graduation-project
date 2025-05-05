package com.GraduationProject.GraduationProject.controller;


import com.GraduationProject.GraduationProject.dto.JwtResponse;
import com.GraduationProject.GraduationProject.dto.UsrDTO;
import com.GraduationProject.GraduationProject.jwt.JwtUtil;
import com.GraduationProject.GraduationProject.repo.UsrRepo;
import com.GraduationProject.GraduationProject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {




    private final UserService userService;

    private final AuthenticationManager authenticationManager;


    @Autowired
    public AuthController(@Lazy UserService userService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
    }


    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UsrDTO usrDTO){
        if(userService.usernameExists(usrDTO.getUsername())){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Bu kullanıcı adı zaten kullanılıyor!");
        }

        userService.register(usrDTO);
        return ResponseEntity.ok("Kayıt başarılı.");
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UsrDTO usrDTO){
        JwtResponse jwtResponse = new JwtResponse();
        try {
            String token = userService.login(usrDTO, authenticationManager); // AuthenticationManager parametresi eklendi
            jwtResponse.setToken(token);
            jwtResponse.setResponseCode(HttpStatus.OK);
            return ResponseEntity.ok(jwtResponse);
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There is an error!");
        }


    }


}
