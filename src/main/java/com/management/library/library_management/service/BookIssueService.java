package com.management.library.library_management.service;

import com.management.library.library_management.entity.Book;
import com.management.library.library_management.entity.BookStatus;
import com.management.library.library_management.entity.IssueReturnDetails;
import com.management.library.library_management.repository.BookIssueRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
public class BookIssueService {
    @Autowired
    private BookIssueRepository bookIssueRepository;
    @Autowired
    private BookService bookService;

    @Transactional
    public void saveIssuedBook(IssueReturnDetails issueReturnDetails) {
        if (issueReturnDetails.getBook() == null || issueReturnDetails.getBook().getId() == null) {
            throw new IllegalArgumentException("Book information is missing in issue details");
        }
        bookIssueRepository.save(issueReturnDetails);
        log.info("Issue return details saved for book ID: {}", issueReturnDetails.getBook().getId());
        Book book = bookService.findBookById(issueReturnDetails.getBook().getId());
        if (book == null) {
            throw new RuntimeException("Book not found with ID: " + issueReturnDetails.getBook().getId());
        }
        book.setStatus(issueReturnDetails.getActualReturnDate() != null
                ? BookStatus.AVAILABLE.name()
                : BookStatus.ISSUED.name());
        log.info("Book status updated to: {}", book.getStatus());
        bookService.saveBook(book);
        log.info("Book updated successfully with ID: {}", book.getId());
    }

}
