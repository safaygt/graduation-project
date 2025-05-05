package com.GraduationProject.GraduationProject.service;

import com.GraduationProject.GraduationProject.dto.UsrDTO;
import com.GraduationProject.GraduationProject.entity.Usr;
import com.GraduationProject.GraduationProject.jwt.JwtUtil;
import com.GraduationProject.GraduationProject.repo.UsrRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Collection;


@Service
public class UserService implements UserDetailsService {



    private final UsrRepo usrRepo;


    private final PasswordEncoder passwordEncoder;

    private final JwtUtil jwtUtil;


    @Autowired
    public UserService(UsrRepo usrRepo, PasswordEncoder passwordEncoder, @Lazy JwtUtil jwtUtil) {
        this.usrRepo = usrRepo;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public boolean usernameExists(String username) {
        return usrRepo.findByUsername(username) != null;
    }

    public void register(UsrDTO usrDTO) {
        Usr usr = new Usr();
        usr.setName(usrDTO.getName());
        usr.setLastName(usrDTO.getLastName());
        usr.setUsername(usrDTO.getUsername());
        usr.setPassword(passwordEncoder.encode(usrDTO.getPassword()));
        usrRepo.save(usr);
    }



    public String login(UsrDTO userDTO, AuthenticationManager authenticationManager) {
        Usr usr = usrRepo.findByUsername(userDTO.getUsername());

        if (usr == null) {
            throw new BadCredentialsException("User not found: " + userDTO.getUsername());
        }

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userDTO.getUsername(), userDTO.getPassword())
            );
        } catch (BadCredentialsException e) {
            System.err.println("Authentication failed for user: " + userDTO.getUsername());
            throw new BadCredentialsException("Wrong username or password!");
        }

        return jwtUtil.generateToken(usr.getUsername(), usr.getId());
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usr usr = usrRepo.findByUsername(username);
        if (usr == null) {
            throw new UsernameNotFoundException("User not found " + username);
        }
        
        return new User(usr.getUsername(), usr.getPassword(), new ArrayList<>());
    }

    public Integer findUserIdByUsername(String username) {
        Usr user = usrRepo.findByUsername(username);
        if (user != null) {
            return user.getId();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with username: " + username);
        }

    }
}
