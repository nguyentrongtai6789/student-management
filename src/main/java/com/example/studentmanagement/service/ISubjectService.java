package com.example.studentmanagement.service;

import com.example.studentmanagement.model.Subject;

import java.util.List;

public interface ISubjectService extends IGenerateService<Subject>{
    List<Subject> findAll();
}
