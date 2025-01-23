package com.management.library.library_management.entity;
import com.management.library.library_management.utils.Reference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "book_issued_log")
public class IssueReturnDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "id", column = @Column(name = "user_id")),
            @AttributeOverride(name = "name", column = @Column(name = "user_name"))
    })
    private Reference user;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "id", column = @Column(name = "book_id")),
            @AttributeOverride(name = "name", column = @Column(name = "book_name"))
    })
    private Reference book;

    @Temporal(TemporalType.DATE)
    private Date issueDate;

    @Temporal(TemporalType.DATE)
    private Date expectedReturnDate;

    @Temporal(TemporalType.DATE)
    private Date actualReturnDate;
}