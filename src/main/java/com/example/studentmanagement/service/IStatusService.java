package com.example.studentmanagement.service;

import com.example.studentmanagement.model.Status;

import java.util.List;

public interface IStatusService extends IGenerateService<Status> {
    public List<Status> findAll();
}
