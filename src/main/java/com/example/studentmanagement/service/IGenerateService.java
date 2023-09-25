package com.example.studentmanagement.service;

import com.example.studentmanagement.repository.IStudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

public interface IGenerateService<E> {

    Page<E> findAll(Pageable pageable);

    Optional<E> findById(Long id);

    void save(E e);

    void deleteById(Long id);
}
