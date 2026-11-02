package com.example.employeemanagement.service;

import com.example.employeemanagement.dto.DepartmentResponse;
import com.example.employeemanagement.dto.EmployeeResponse;
import com.example.employeemanagement.dto.EmployeeSummaryResponse;
import com.example.employeemanagement.entity.Department;
import com.example.employeemanagement.exception.ResourceNotfoundException;
import com.example.employeemanagement.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;
@Service
public class DepartmentServiceImpl implements DepartmentService {


    @Autowired
    private DepartmentRepository departmentRepository;

    @Override
    public DepartmentResponse getDepartmentById(Long id) {
        Department department = departmentRepository.findById(id).orElseThrow(() -> new ResourceNotfoundException("Departments not found with id :" + id));
        List<EmployeeSummaryResponse> employees = department.getEmployees().stream().map(employee ->
                        new EmployeeSummaryResponse(employee.getId(), employee.getName()))
                .toList();
        return new DepartmentResponse(department.getId(), department.getName(), employees);

    }

    @Override
    public List<DepartmentResponse> getAllDepartments() {
        List<Department> departments = departmentRepository.findAllWithEmployees();
        return departments.stream().map(department -> {
            List<EmployeeSummaryResponse> employees = department.getEmployees().stream().map(employee -> new EmployeeSummaryResponse(employee.getId(), employee.getName()))
                    .toList();

            return new DepartmentResponse(department.getId(), department.getName(), employees);
        })
            .toList();
    }
}
