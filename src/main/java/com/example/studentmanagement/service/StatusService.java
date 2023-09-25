package com.example.studentmanagement.service;

import com.example.studentmanagement.model.Status;
import com.example.studentmanagement.repository.IStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.Optional;
@Service
public class StatusService implements IStatusService{
    @Autowired
    private IStatusRepository statusRepository;
    @Override
    public Page<Status> findAll(Pageable pageable) {
        return statusRepository.findAll(pageable);
    }

    @Override
    public Optional<Status> findById(Long id) {
        return statusRepository.findById(id);
    }

    @Override
    public void save(Status status) {
        statusRepository.save(status);
    }

    @Override
    public void deleteById(Long id) {
        statusRepository.deleteById(id);
    }
}
