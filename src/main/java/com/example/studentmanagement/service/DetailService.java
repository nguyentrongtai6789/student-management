package com.example.studentmanagement.service;

import com.example.studentmanagement.model.DetailStudentAndSubject;
import com.example.studentmanagement.model.Student;
import com.example.studentmanagement.repository.IDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class DetailService implements IDetailService{
    @Autowired
    private IDetailRepository detailRepository;

    @Override
    public List<DetailStudentAndSubject> findAllList() {
        return detailRepository.findAll();
    }

    @Override
    public void deleteAllByStudent_Id(Long id) {
        detailRepository.deleteAllByStudent_Id(id);
    }

    @Override
    public List<DetailStudentAndSubject> findAllByStudent(Student student) {
        return detailRepository.findAllByStudent(student);
    }

    @Override
    public List<DetailStudentAndSubject> selectAllByStudent_Id(Long id) {
        return detailRepository.selectAllByStudent_Id(id);
    }

    @Override
    public Page<DetailStudentAndSubject> findAll(Pageable pageable) {
        return detailRepository.findAll(pageable);
    }

    @Override
    public Optional<DetailStudentAndSubject> findById(Long id) {
        return detailRepository.findById(id);
    }

    @Override
    public void save(DetailStudentAndSubject detailStudentAndSubject) {
        detailRepository.save(detailStudentAndSubject);
    }

    @Override
    public void deleteById(Long id) {
        detailRepository.deleteById(id);
    }
    public List<DetailStudentAndSubject> findAll(){
        List<DetailStudentAndSubject> list = detailRepository.findAll();
        return list;
    }

    public List<DetailStudentAndSubject> findAllDetailStudentByStudent(Student student){
        return detailRepository.listDetailStudentAndSubject(student);
    }
}
