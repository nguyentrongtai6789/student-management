package com.example.studentmanagement.service;

import com.example.studentmanagement.model.DetailStudentAndSubject;
import com.example.studentmanagement.model.Student;
import com.example.studentmanagement.model.Subject;
import com.example.studentmanagement.repository.ISubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.xml.soap.Detail;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class SubjectService implements ISubjectService{
    @Autowired
    private ISubjectRepository subjectRepository;
    @Autowired
    private DetailService detailService;
    @Autowired
    private StudentService studentService;

    @Override
    public List<Subject> findAll() {
        return subjectRepository.findAll();
    }

    @Override
    public Page<Subject> findAll(Pageable pageable) {
        return subjectRepository.findAll(pageable);
    }

    @Override
    public Optional<Subject> findById(Long id) {
        return subjectRepository.findById(id);
    }

    @Override
    public void save(Subject subject) {
        subjectRepository.save(subject);
    }

    @Override
    public void deleteById(Long id) {
        subjectRepository.deleteById(id);
    }
    public List<Subject> subjectListByIdStudent(Long id_student){
        List<DetailStudentAndSubject> list = detailService.findAll();
        List<Subject> list1 = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getStudent().getId().equals(id_student)){
                list1.add(list.get(i).getSubject());
            }
        }
        return list1;
    }
}
