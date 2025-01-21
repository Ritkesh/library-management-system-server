package com.management.library.library_management.controller;

import com.management.library.library_management.entity.User;
import com.management.library.library_management.service.UserDetailsServiceImpl;
import com.management.library.library_management.service.UserService;
import com.management.library.library_management.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

//@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")

@RestController
@RequestMapping("/rest")
@Slf4j

public class PublicController {
    @Autowired
    UserService userService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @PostMapping("/signup")
    public ResponseEntity<?>signUp(@RequestBody User user){
        try {
            userService.saveNewUser(user);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("login")
    public ResponseEntity<?>logIn(@RequestBody User user){
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassword()));
            UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUserName());
            String jwt = jwtUtil.generateToken(userDetails.getUsername());
            User userObject = userService.findUserByUserName(user.getUserName());
            Map<String, String> response = new HashMap<>();
            response.put("token", jwt);
            if (userObject.getRoles()!=null && userObject.getRoles().size()>0 && userObject.getRoles().contains("ADMIN")) response.put("role", "ADMIN");
            return ResponseEntity.ok(response);
        } catch (Exception e){
            log.error("Exception occurred while generating token");
            return new ResponseEntity<>("Incorrect user name password",HttpStatus.BAD_REQUEST);
        }
    }
}
