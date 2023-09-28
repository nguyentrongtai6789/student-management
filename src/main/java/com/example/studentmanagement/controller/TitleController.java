package com.example.studentmanagement.controller;

import com.example.studentmanagement.model.Student;
import com.example.studentmanagement.model.Subject;
import com.example.studentmanagement.model.Title;
import com.example.studentmanagement.service.StudentService;
import com.example.studentmanagement.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/title")
public class TitleController {
    @Autowired
    private StudentService studentService;
    @Autowired
    private SubjectService subjectService;


    @PostMapping
    public ResponseEntity<List<Student>> searchByTitle(@RequestBody Title title) {
        List<Student> list = new ArrayList<>();
        List<Student> students = studentService.findAll();
        for (Student student : students) {
            if (student.getAddress().equals(title.getAddress()) &&
                    student.getStatus().getId().equals(title.getStatus())) {
                list.add(student);
            }
        }
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

//    @GetMapping
//    public ResponseEntity<List<Student>> showListStudentByTitle() {
//        return new ResponseEntity<>(list, HttpStatus.OK);
//    }
}