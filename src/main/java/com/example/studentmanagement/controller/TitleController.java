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
    private List<Student> list = new ArrayList<>();

    @PostMapping
    public ResponseEntity<List<Student>> searchByTitle(@RequestBody Title title){
        List<Student> students = studentService.findAll();
        for (int i = 0; i < students.size(); i++) {

            if (students.get(i).getAddress().equals(title.getAddress()) &&
            students.get(i).getStatus().getId() == title.getStatus()){
                list.add(students.get(i));
            }
        }
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<List<Student>> showListStudentByTitle(){
        ResponseEntity<List<Student>> response = new ResponseEntity<>(list, HttpStatus.OK);
        return response;
    }
}
