package com.example.studentmanagement.controller;

import com.example.studentmanagement.model.DetailStudentAndSubject;
import com.example.studentmanagement.service.DetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/detail")
public class DetailStudentAndSubjectController {
    @Autowired
    private DetailService detailService;
    @GetMapping("/detail_student_and_subject/{id_student}")
    public ResponseEntity<List<DetailStudentAndSubject>> listDetailByStudentId(@PathVariable("id_student") int id_student){
        ResponseEntity<List<DetailStudentAndSubject>> list = new ResponseEntity<>(detailService.findAllByStudentId(id_student), HttpStatus.OK);
        return list;
    }
}
