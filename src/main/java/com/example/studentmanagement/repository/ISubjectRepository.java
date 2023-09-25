package com.example.studentmanagement.repository;

import com.example.studentmanagement.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface ISubjectRepository extends JpaRepository<Subject, Long> {
}
