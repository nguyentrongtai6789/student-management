package com.example.studentmanagement.controller;

import com.example.studentmanagement.model.DetailStudentAndSubject;
import com.example.studentmanagement.model.Subject;
import com.example.studentmanagement.service.DetailService;
import com.example.studentmanagement.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Action;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/subject")
public class SubjectController {
    @Autowired
    private DetailService detailService;
    @Autowired
    private SubjectService subjectService;
    @GetMapping("/detail_student_and_subject/{id_student}")
    public ResponseEntity<List<Subject>> listDetailByStudentId(@PathVariable("id_student") Long id_student){
        List<Subject> subjectList = subjectService.subjectListByIdStudent(id_student);
        ResponseEntity<List<Subject>> response = new ResponseEntity<>(subjectList, HttpStatus.OK);
        return response;
    }
}
