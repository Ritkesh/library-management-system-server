package com.management.library.library_management.repository;

import com.management.library.library_management.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
public interface StudentRepo extends JpaRepository<Student,Integer> {
}
