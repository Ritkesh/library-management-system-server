package com.management.library.library_management.service;

import com.management.library.library_management.entity.User;
import com.management.library.library_management.entity.UserDto;
import com.management.library.library_management.entity.UserRole;
import com.management.library.library_management.repository.UserRepository;
import com.management.library.library_management.utils.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    public void saveNewUser(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if(user.getRoles()== null){
            user.setRoles(Arrays.asList("USER"));
        }
        userRepository.save(user);
    }
    public void saveNewAdminUser(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if(user.getRoles()== null){
            user.setRoles(Arrays.asList(UserRole.ADMIN.name(),UserRole.USER.name()));
        }
        userRepository.save(user);
    }

    public void saveUser(User user){
        userRepository.save(user);
    }
    public List<User> getAll(){
        List<User> userList = userRepository.findAll();
        return userList;

    }
    public User findUserByUserName(String name){
        return userRepository.findByName(name);
    }
    public List<Reference> findALlUser(){
        return userRepository.findAll().stream()
                .map(user->new Reference(user.getId(),user.getName()))
                .toList();
    }
}
