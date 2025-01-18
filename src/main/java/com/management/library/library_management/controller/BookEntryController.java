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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
                return new ResponseEntity(HttpStatus.CREATED);
            }
            else{
                return new ResponseEntity(HttpStatus.BAD_REQUEST);
            }
        }catch (Error e)
        {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

    }
}
