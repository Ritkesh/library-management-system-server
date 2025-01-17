package com.management.library.library_management.controller;

import com.management.library.library_management.entity.Student;
import com.management.library.library_management.repository.StudentRepo;
import com.management.library.library_management.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/student")


public class StudentController {
    @Autowired
    private StudentService studentService;
    @PostMapping
    public ResponseEntity<?> createStudent(@RequestBody Student student){
        studentService.saveStudent(student);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable Integer id){
        studentService.deleteStudentById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
