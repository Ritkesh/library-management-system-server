package com.management.library.library_management.service;

import com.management.library.library_management.entity.Book;
import com.management.library.library_management.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BookService {
    @Autowired
    BookRepository bookRepository;

    public void saveBook(Book book){
        bookRepository.save(book);
    }
}
