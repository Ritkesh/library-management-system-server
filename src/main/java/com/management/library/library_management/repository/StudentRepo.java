package com.management.library.library_management.repository;

import com.management.library.library_management.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Component;

;
@EnableJpaRepositories
public interface StudentRepo extends JpaRepository<Student,Integer> {


}
