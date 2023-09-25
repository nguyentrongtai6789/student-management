package com.example.studentmanagement.repository;

import com.example.studentmanagement.model.DetailStudentAndSubject;
import com.example.studentmanagement.model.Student;
import com.example.studentmanagement.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface IDetailRepository extends JpaRepository<DetailStudentAndSubject, Long> {
    @Transactional
    @Modifying
    @Query(value = "delete from detail where id_student = ?1", nativeQuery = true)
    void deleteAllByStudent_Id(Long id);
//    @Transactional
//    @Modifying
//    @Query(value = "select * from detail where id_student = ?1;", nativeQuery = true)
//    List<DetailStudentAndSubject> getSubjectCheckedByStudent(Long id); // lấy ra list detail của student đó
    List<DetailStudentAndSubject> findAllByStudent(Student student);
    @Query(value = "delete from detail where id_student = ?1", nativeQuery = true)
    List<DetailStudentAndSubject> findAllByStudentId(int id_student);
}
