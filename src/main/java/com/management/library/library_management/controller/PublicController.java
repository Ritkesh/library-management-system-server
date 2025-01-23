package com.management.library.library_management.controller;

import com.management.library.library_management.entity.User;
import com.management.library.library_management.entity.UserRole;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

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
// for user sign up
    @PostMapping("/signup")
    public ResponseEntity<?>signUp(@RequestBody User user){
        try {
            userService.saveNewUser(user);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    // for user login
    @PostMapping("/login")
    public ResponseEntity<?>logIn(@RequestBody User user){
        try {
            Map<String,String> response = new HashMap<>();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getName(),user.getPassword()));
            UserDetails userDetails = userDetailsService.loadUserByUsername(user.getName());
            String jwt = jwtUtil.generateToken(userDetails.getUsername());
            response.put("token",jwt);
            User userObj = userService.findUserByUserName(user.getName());
            if(userObj.getRoles()!=null && !(userObj.getRoles().isEmpty()) && userObj.getRoles().contains(UserRole.ADMIN.name())){
                response.put("role",UserRole.ADMIN.name());
            }
            return ResponseEntity.ok(response);
        } catch (Exception e){
            log.error("Exception occurred while generating token");
            return new ResponseEntity<>("Incorrect user name password",HttpStatus.BAD_REQUEST);
        }
    }
}
