package com.example.studentmanagement.repository;

import com.example.studentmanagement.model.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IStudentRepository extends JpaRepository<Student, Long> {
    @Query(value = "select * from student where name like %?%", nativeQuery = true)
    Page<List<Student>> searchByName(String name, Pageable pageable);
}
