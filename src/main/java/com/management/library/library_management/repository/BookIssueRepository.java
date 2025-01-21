package com.management.library.library_management.repository;

import com.management.library.library_management.entity.IssueReturnDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookIssueRepository extends JpaRepository<IssueReturnDetails,Integer> {
}
