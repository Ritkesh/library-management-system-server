package com.management.library.library_management.utils;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class Reference {
    @Column(name = "id",insertable=false, updatable=false)
    private Integer id;
    @Column(name = "name",insertable=false, updatable=false)
    private String name;

    public Reference(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    // Default constructor (required for JPA)
    public Reference() {}

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

