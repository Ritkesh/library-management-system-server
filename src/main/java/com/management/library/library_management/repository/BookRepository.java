package com.management.library.library_management.repository;

import com.management.library.library_management.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book,Integer> {
    List<Book> findByNameContainingIgnoreCase(String bookName);
    List<Book> findByAuthorNameContainingIgnoreCase(String authorName);
    List<Book> findByNameContainingIgnoreCaseAndAuthorNameContainingIgnoreCase(String bookName, String authorName);
    List<Book> findByStatus(String status);

}
