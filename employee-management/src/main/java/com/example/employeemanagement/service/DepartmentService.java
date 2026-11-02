package com.example.employeemanagement.service;

import com.example.employeemanagement.dto.DepartmentResponse;

import java.util.List;

public interface DepartmentService {
    DepartmentResponse getDepartmentById(Long id);
    List<DepartmentResponse> getAllDepartments();
}
