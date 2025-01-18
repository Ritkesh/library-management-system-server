package com.management.library.library_management.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
@Entity
public class User {
    @Id
    private Integer id ;
    private String userName;
    private String password;
    private List<String> roles;
}
