package com.management.library.library_management.service;

import com.management.library.library_management.entity.Student;
import com.management.library.library_management.repository.StudentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StudentService {
    @Autowired
    private StudentRepo studentrepo;

    public void saveStudent(Student student){
        studentrepo.save(student);

    }

    public void deleteStudentById(Integer id) {
        studentrepo.deleteById(id);
    }
}
