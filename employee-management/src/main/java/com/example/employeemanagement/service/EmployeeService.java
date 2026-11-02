package com.example.employeemanagement.service;

import com.example.employeemanagement.dto.*;
import com.example.employeemanagement.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;

public interface EmployeeService {
    EmployeeResponse createEmployee(EmployeeCreateRequest request);
    //List<EmployeeResponse> getAllEmployees();
    Page<EmployeeResponse> getAllEmployees(int page,int size,String sortBy,String direction);
    EmployeeResponse getEmployeeById(Long id);
    EmployeeResponse updateEmployee(Long id, EmployeeUpdateRequest employeeUpdateRequest);
    void deleteEmployee(Long id);
   EmployeeResponse patchEmployee(Long id , EmployeePatchRequest employeePatchRequest);

   List<EmployeeResponse> getEmployeesByDepartment(String department);
   List<EmployeeResponse> getEmployeesByName(String name);
   List<EmployeeResponse> getEmployeesBySalaryGreaterThanEqual(BigDecimal salary);
   Page<EmployeeResponse> searchEmployees(String department,String name,BigDecimal minSalary,int page, int size, String sortBy, String direction);

   EmployeeResponse promoteEmployee(Long id,Long department,BigDecimal salary);

   List<EmployeeBasicResponse> getBasicEmployeesByDepartment(String departmentName);

   Page<EmployeeSearchResponse> searchEmployees(Pageable pageable);

   EmployeeDetailsResponse getEmployeeDetails(Long id);
}
