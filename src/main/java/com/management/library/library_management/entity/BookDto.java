package com.management.library.library_management.entity;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class BookDto implements Serializable {
    private String bookName;
    private Integer id;

    public BookDto(String bookName, Integer id) {
        this.bookName = bookName;
        this.id = id;
    }
}
