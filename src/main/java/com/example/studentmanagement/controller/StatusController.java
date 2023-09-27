package com.example.studentmanagement.controller;

import com.example.studentmanagement.model.Status;
import com.example.studentmanagement.service.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/status")
public class StatusController {
    @Autowired
    private StatusService statusService;

    @GetMapping
    public ResponseEntity<List<String>> findAll(){
        List<String> nameStatus = new ArrayList<>();
        List<Status> statusList = statusService.findAll();
        for (int i = 0; i < statusList.size(); i++) {
            nameStatus.add(statusList.get(i).getName());
        }
        ResponseEntity<List<String>> response = new ResponseEntity<>(nameStatus, HttpStatus.OK);
        return response;
    }
}
