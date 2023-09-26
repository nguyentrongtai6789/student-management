package com.example.studentmanagement.service;

import com.example.studentmanagement.model.DetailStudentAndSubject;
import com.example.studentmanagement.model.Student;
import com.example.studentmanagement.model.Subject;

import javax.xml.soap.Detail;
import java.util.List;

public interface IDetailService extends IGenerateService<DetailStudentAndSubject>{
    void deleteAllByStudent_Id(Long id);
    List<DetailStudentAndSubject> findAllByStudent(Student student);
    List<DetailStudentAndSubject> selectAllByStudent_Id(Long id);
}
