package com.management.library.library_management.repository;

import com.management.library.library_management.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book,Integer> {
    List<Book> findByBookNameContainingIgnoreCase(String bookName);
    List<Book> findByAuthorNameContainingIgnoreCase(String authorName);
    List<Book> findByBookNameContainingIgnoreCaseAndAuthorNameContainingIgnoreCase(String bookName, String authorName);

}
