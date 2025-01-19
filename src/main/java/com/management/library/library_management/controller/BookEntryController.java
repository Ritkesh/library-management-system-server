package com.management.library.library_management.controller;

import com.management.library.library_management.entity.Book;
import com.management.library.library_management.entity.User;
import com.management.library.library_management.service.BookService;
import com.management.library.library_management.utils.CommonUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book")
@Slf4j
public class BookEntryController {
    @Autowired
    BookService bookService;
    @Autowired
    CommonUtils commonUtils;
    @PostMapping("/entry")
    public ResponseEntity<?> bookEntry(@RequestBody Book book, HttpServletRequest request){
        try {

             User user = commonUtils.getApplicationUser(request);

            if(user.getRoles().contains("ADMIN")){
                bookService.saveBook(book);
                return new ResponseEntity<>(HttpStatus.CREATED);
            }
        }catch (Error e)
        {
            log.error("Error while book entry",e.getMessage());
        }

        return new ResponseEntity<>(HttpStatus.FORBIDDEN);

    }
    @PutMapping("/update-book-detail")
    public ResponseEntity<?> updateBookDetail(@RequestBody Book book,HttpServletRequest request){
        try{
            User user = commonUtils.getApplicationUser(request);
            if(user.getRoles().contains("ADMIN")){
                Book old = bookService.findBookById(book.getId());
                if(old!=null){
                    old.setBookName(book.getBookName());
                    old.setAuthorName(book.getAuthorName());
                    bookService.saveBook(old);
                    return new ResponseEntity<>(HttpStatus.OK);
                }
            }

        } catch (Exception e){
            log.error("Error occurred while updating book details",e.getMessage());
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }
    @GetMapping("/book-list")
    public ResponseEntity<?> showBookList(){
        try{
            List<Book>bookList = bookService.getAllBook();
            if(!bookList.isEmpty()){
                return new ResponseEntity<>(bookList,HttpStatus.OK);
            }
        } catch (Exception e){
            log.error("Error while getting bookList",e.getMessage());
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

}
