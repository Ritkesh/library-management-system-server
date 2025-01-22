package com.management.library.library_management.service;

import com.management.library.library_management.entity.Book;
import com.management.library.library_management.entity.BookDto;
import com.management.library.library_management.entity.BookStatus;
import com.management.library.library_management.entity.IssueReturnDetails;
import com.management.library.library_management.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class BookService {
    @Autowired
    BookRepository bookRepository;

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
    public List<BookDto> getBookList() {
        return bookRepository.findAll().stream()
                .map(book -> new BookDto(book.getBookName(), book.getId()))
                .toList();
    }
}
