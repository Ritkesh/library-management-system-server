package com.management.library.library_management.controller;

import com.management.library.library_management.entity.*;
import com.management.library.library_management.service.BookIssueService;
import com.management.library.library_management.service.BookService;
import com.management.library.library_management.service.UserService;
import com.management.library.library_management.utils.CommonUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/book")
@Slf4j
public class BookEntryController {
    @Autowired
    private BookService bookService;
    @Autowired
    private CommonUtils commonUtils;
    @Autowired
    private BookIssueService bookIssueService;
    @Autowired
    private UserService userService;

    @PostMapping("/entry")
    public ResponseEntity<?> bookEntry(@RequestBody Book book, HttpServletRequest request) {
        try {

            User user = commonUtils.getApplicationUser(request);

            if (user.getRoles().contains("ADMIN")) {
                book.setStatus(BookStatus.AVAILABLE.name());
                bookService.saveBook(book);
                return new ResponseEntity<>(HttpStatus.CREATED);
            }
        } catch (Error e) {
            log.error("Error while book entry", e.getMessage());
        }

        return new ResponseEntity<>(HttpStatus.FORBIDDEN);

    }

    @PutMapping("/update-book-detail")
    public ResponseEntity<?> updateBookDetail(@RequestBody Book book, HttpServletRequest request) {
        try {
            User user = commonUtils.getApplicationUser(request);
            if (user.getRoles().contains("ADMIN")) {
                bookService.saveBook(book);
                return new ResponseEntity<>(HttpStatus.OK);

            }

        } catch (Exception e) {
            log.error("Error occurred while updating book details", e.getMessage());
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @GetMapping("/book-list")
    public ResponseEntity<?> showBookList() {
        try {
            List<Book> bookList = bookService.getAllBook();
            bookList.sort(Comparator.comparing(Book::getId));
            if (!bookList.isEmpty()) {
                return new ResponseEntity<>(bookList, HttpStatus.OK);
            }
        } catch (Exception e) {
            log.error("Error while getting bookList", e.getMessage());
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @GetMapping("/return-book-list")
    public ResponseEntity<?> showReturnBookList() {
        try {
            List<Book> bookList = bookService.getAllBook();
            bookList.sort(Comparator.comparing(Book::getId));
            List<Book> bookReturnList = bookList.stream()
                    .filter(book -> book.getStatus().equals(BookStatus.ISSUED.name()))
                    .collect(Collectors.toList());
            if (!bookReturnList.isEmpty()) {
                return new ResponseEntity<>(bookReturnList, HttpStatus.OK);
            }
        } catch (Exception e) {
            log.error("Error while getting bookReturnList", e.getMessage());
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @GetMapping("/search")
    public ResponseEntity<?> searchBooks(
            @RequestParam(required = false) String bookName,
            @RequestParam(required = false) String authorName) {
        try {
            List<Book> books = bookService.searchBooks(bookName, authorName);
            if (!books.isEmpty()) {
                return new ResponseEntity<>(books, HttpStatus.OK);
            }
        } catch (Exception e) {
            log.error("Error while searching books: {}", e.getMessage());
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/issue-book")
    public ResponseEntity<?> issueBook(@RequestBody IssueReturnDetails issueReturnDetails) {
        try {
            bookIssueService.saveIssuedBook(issueReturnDetails);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error while issuing books: {}", e.getMessage());
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/delete-book")
    public ResponseEntity<?> deleteBook(@RequestBody Book book) {
        try {
            bookService.deleteBookById(book.getId());
        } catch (Exception e) {
            log.error("Error while deleting book", e.getMessage());
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/get-book")
    public ResponseEntity<?> getBookById(@RequestBody Book book) {
        try {
            Book bookObj = bookService.findBookById(book.getId());
            return new ResponseEntity<>(bookObj, HttpStatus.OK);
        } catch (Exception e) {
            log.info("Error while getting book", e.getMessage());
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    @GetMapping("/user")
    public ResponseEntity<List<UserDto>>getAllUser(){
        try {
            List<UserDto>userDtoList = userService.findALlUser();
            return new ResponseEntity<>(userDtoList,HttpStatus.OK);
        } catch (Exception e){
            log.info("Error while fetching user",e.getMessage());
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @GetMapping("/all-book")
    public ResponseEntity<List<BookDto>>getAllBooks(){
        try {
            List<BookDto>bookDtoList = bookService.getBookList();
            return new ResponseEntity<>(bookDtoList,HttpStatus.OK);
        } catch (Exception e){
            log.info("Error while fetching user",e.getMessage());
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/return-book")
    public ResponseEntity<?> getBook(IssueReturnDetails issueReturnDetails){
        try {
            BookDto bookObj = bookService.getIssuedBookById(issueReturnDetails.getId());
            return new ResponseEntity<>(bookObj,HttpStatus.OK);
        } catch (Exception e){
            log.info("Error while fetching user",e.getMessage());
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
