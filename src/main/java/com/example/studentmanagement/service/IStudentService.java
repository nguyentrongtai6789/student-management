package com.example.studentmanagement.service;

import com.example.studentmanagement.model.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IStudentService extends IGenerateService<Student>{
    Page <List<Student>> searchByName(String name, Pageable pageable);
}
