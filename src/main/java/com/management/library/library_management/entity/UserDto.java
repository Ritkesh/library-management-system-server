package com.management.library.library_management.entity;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class UserDto implements Serializable {
    private String userName;
    private Integer id;


    public UserDto(Integer id, String userName) {
        this.id = id;
        this.userName = userName;
    }
}
