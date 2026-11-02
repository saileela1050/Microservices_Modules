package com.example.employeemanagement.controller;

import com.example.employeemanagement.dto.DepartmentResponse;
import com.example.employeemanagement.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/departments")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @GetMapping("{id}")
    public DepartmentResponse getDepartmentById(@PathVariable Long id){
        return departmentService.getDepartmentById(id);
    }
    @GetMapping
    public List<DepartmentResponse> getAllDepartments(){
        return departmentService.getAllDepartments();
    }
}
