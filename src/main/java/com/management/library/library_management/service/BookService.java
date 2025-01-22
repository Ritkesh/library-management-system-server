package com.management.library.library_management.service;

import com.management.library.library_management.entity.*;
import com.management.library.library_management.repository.BookIssueRepository;
import com.management.library.library_management.repository.BookRepository;
import com.management.library.library_management.utils.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
public class BookService {
    @Autowired
    BookRepository bookRepository;
    @Autowired
    BookIssueRepository bookIssueRepository;

    public void saveBook(Book book) {
        if(book.getStatus() == null){
            book.setStatus(BookStatus.AVAILABLE.name());
        }

        bookRepository.save(book);
    }

    public List<Book> getAllBook() {
        return bookRepository.findAll();
    }

    public Book findBookById(Integer id) {
        return bookRepository.findById(id).orElse(null);
    }

    public List<Book> getAllBooksByStatus(String status) {
        return bookRepository.findByStatus(status);
    }
    public void deleteBookById(Integer id){
         bookRepository.deleteById(id);
    }


    public List<Book> searchBooks(String bookName, String authorName) {
        if (bookName != null && authorName != null) {
            return bookRepository.findByBookNameContainingIgnoreCaseAndAuthorNameContainingIgnoreCase(bookName.trim(), authorName.trim());
        } else if (bookName != null) {
            return bookRepository.findByBookNameContainingIgnoreCase(bookName.trim());
        } else if (authorName != null) {
            return bookRepository.findByAuthorNameContainingIgnoreCase(authorName.trim());
        }
        return Collections.emptyList();
    }
    public List<Reference> getBookList() {
        return bookRepository.findAll().stream()
                .filter(book -> book.getStatus().equals(BookStatus.AVAILABLE.name()))
                .map(book -> new Reference( book.getId(),book.getBookName()))
                .toList();
    }
    public IssueReturnDetails getIssuedBookById(Integer id) {
        IssueReturnDetails issueReturnDetails =  bookIssueRepository.findById(id).orElse(null);
        return null;
    }
}
