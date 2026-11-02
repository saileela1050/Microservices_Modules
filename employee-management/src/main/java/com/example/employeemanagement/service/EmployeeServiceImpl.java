

package com.example.employeemanagement.service;

import com.example.employeemanagement.client.DepartmentClient;
import com.example.employeemanagement.dto.*;
import com.example.employeemanagement.entity.Department;
import com.example.employeemanagement.entity.Employee;
import com.example.employeemanagement.exception.ResourceNotfoundException;
import com.example.employeemanagement.repository.DepartmentRepository;
import com.example.employeemanagement.repository.EmployeeRepository;
import com.example.employeemanagement.specification.EmployeeSpecification;
import jakarta.transaction.Transactional;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Service

public class EmployeeServiceImpl implements EmployeeService {

    //    private  final EmployeeRepository employeeRepository;
//
//    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
//        this.employeeRepository = employeeRepository;
//    }

    // logging
    // private static final Logger log= LoggerFactory.getLogger(EmployeeServiceImpl.class);

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private DepartmentClient departmentClient;


    @Override
    public EmployeeResponse createEmployee(EmployeeCreateRequest request) {
        log.info("Creating employee{}", request);
        Employee employee = new Employee();
        employee.setName(request.getName());
        employee.setEmail(request.getEmail());
        employee.setSalary(request.getSalary());
        //employee.setDepartment(request.getDepartment());
        employee.setJoiningDate(request.getJoiningDate());

        Department department=departmentRepository.findById(request.getDepartmentId()).orElseThrow(()->new ResourceNotfoundException("department id is not found" +request.getDepartmentId()));
        employee.setDepartment(department);
        log.info("saving employee {} ", request);
        Employee savedEmployee = employeeRepository.save(employee);
        log.info("Employee saved successfully with id: {}", savedEmployee.getId());
        return new EmployeeResponse(
                savedEmployee.getId(),
                savedEmployee.getName(),
                savedEmployee.getEmail(),
                savedEmployee.getSalary(),
                savedEmployee.getDepartment().getName(),
                savedEmployee.getJoiningDate()
        );
    }

//    @Override
//    public List<EmployeeResponse> getAllEmployees() {
//        List<Employee> employees = employeeRepository.findAll();
//
//        return employees.stream().map(employee -> new EmployeeResponse(
//                        employee.getId(),
//                        employee.getName(),
//                        employee.getEmail(),
//                        employee.getSalary(),
//                        employee.getDepartment(),
//                        employee.getJoiningDate()))
//                .toList();
//    }

    @Override
    public Page<EmployeeResponse> getAllEmployees(int page,int size,String sortBy,String direction) {
        log.info("Fetching all employees page:{},size:{},sortby:{},direction:{}", size,page,sortBy,direction);

        Sort sort;
        if(direction.equalsIgnoreCase("desc")){
            sort=Sort.by(sortBy).descending();
        }else {
            sort=Sort.by(sortBy).ascending();
        }
        Pageable pageable= PageRequest.of(page,size,sort);

        Page<Employee> employeePage = employeeRepository.findAll(pageable);

        return employeePage.map(employee-> new EmployeeResponse(
                employee.getId(),
                employee.getName(),
                employee.getEmail(),
                employee.getSalary(),
                employee.getDepartment().getName(),
                employee.getJoiningDate()));
    }

//    @Override
//    public Employee getEmployeeById(Long id) {
//        return employeeRepository.findById(id).orElseThrow(() ->
//                new RuntimeException("Employee not found with id: " + id));
//    }

    @Override
    public EmployeeResponse getEmployeeById(Long id) {
        log.info("Fetching employee with id:{}", id);
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotfoundException("Employee not found with id: " + id));
        log.info("Employee found with id:{}", id);
        return new EmployeeResponse(
                employee.getId(),
                employee.getName(),
                employee.getEmail(),
                employee.getSalary(),
                employee.getDepartment().getName(),
                employee.getJoiningDate());
    }

    @Override
    public EmployeeResponse updateEmployee(Long id, EmployeeUpdateRequest employeeUpdateRequest) {
        log.info("updating employee with id:{},{}", id,employeeUpdateRequest);

        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Employee not found with id:" + id));
        employee.setName(employeeUpdateRequest.getName());
        employee.setEmail(employeeUpdateRequest.getEmail());
        employee.setSalary(employeeUpdateRequest.getSalary());
        employee.setJoiningDate(employeeUpdateRequest.getJoiningDate());
        Department department=departmentRepository.findById(employeeUpdateRequest.getDepartmentId()).orElseThrow(()->new ResourceNotfoundException("department id is not found:" +employeeUpdateRequest.getDepartmentId()));

        // Department department=departmentRepository.findById(EmployeeUpdateRequest.getDepartmentid().orElseThrow(()-> new ResourceNotfoundException("department id is not found "+EmployeeUpdateRequest.getDepartmentid());
        employee.setDepartment(department);
        //employee.setDepartment(employeeUpdateRequest.getDepartment());
        Employee updatedEmployee=employeeRepository.save(employee);
        log.info(" employee updated successfully with id:{}", id);

        return new EmployeeResponse(updatedEmployee.getId(),
                updatedEmployee.getName(),
                updatedEmployee.getEmail(),
                updatedEmployee.getSalary(),
                updatedEmployee.getDepartment().getName(),
                updatedEmployee.getJoiningDate());

    }

    @Override
    public void deleteEmployee(Long id) {
        log.info("deleting employee with id:{}", id);
        Employee employee = employeeRepository.findById(id).orElseThrow(() ->
                new ResourceNotfoundException("employee id is not there "));
        log.info("suceesfully deleted with employee id{}",id);
        employeeRepository.delete(employee);
    }

    @Override
    public EmployeeResponse patchEmployee(Long id, EmployeePatchRequest employeePatchRequest) {
        log.info("updating employee with id:{},{}", id,employeePatchRequest);

        Employee existingEmployee = employeeRepository.findById(id).orElseThrow(() -> new ResourceNotfoundException(("employee not found with id:" + id)));

        if (employeePatchRequest.getName() != null) {
            existingEmployee.setName(employeePatchRequest.getName());
        }

        if (employeePatchRequest.getEmail() != null) {
            existingEmployee.setEmail(employeePatchRequest.getEmail());
        }
        if (employeePatchRequest.getSalary() != null) {
            existingEmployee.setSalary((employeePatchRequest.getSalary()));
        }
        if (employeePatchRequest.getDepartmentid() != null) {
            Department department=departmentRepository.findById(employeePatchRequest.getDepartmentid()).orElseThrow(()->new ResourceNotfoundException("department id is not found: " +employeePatchRequest.getDepartmentid()));
            existingEmployee.setDepartment(department);

            //  existingEmployee.setDepartment((employeePatchRequest.getDepartmentid().ge));
        }

        if (employeePatchRequest.getJoiningDate() != null) {
            existingEmployee.setJoiningDate((employeePatchRequest.getJoiningDate()));
        }
        // request updatedEmployee = employeeRepository.save(existingEmployee);
        Employee updatedEmployee = employeeRepository.save(existingEmployee);
        log.info("updated employee with id:{}", id);

        return new EmployeeResponse(
                updatedEmployee.getId(),
                updatedEmployee.getName(),
                updatedEmployee.getEmail(),
                updatedEmployee.getSalary(),
                updatedEmployee.getDepartment().getName(),
                updatedEmployee.getJoiningDate()
        );

    }

    @Override
    public List<EmployeeResponse> getEmployeesByDepartment(String department) {
        log.info("fetching employee with department{}", department);

        List<Employee> employees=employeeRepository.findByDepartment(department);

        return employees.stream().map(employee-> new EmployeeResponse(
                        employee.getId(),
                        employee.getName(),
                        employee.getEmail(),
                        employee.getSalary(),
                        employee.getDepartment().getName(),
                        employee.getJoiningDate()))
                .toList();
    }

    @Override
    public List<EmployeeResponse> getEmployeesByName(String name) {
        log.info("fetching employee with name{}", name);

        List<Employee> employees=employeeRepository.findByNameContainingIgnoreCase(name);
        return employees.stream().map(employee -> new EmployeeResponse(
                        employee.getId(),
                        employee.getName(),
                        employee.getEmail(),
                        employee.getSalary(),
                        employee.getDepartment().getName(),
                        employee.getJoiningDate()))
                .toList();
    }

    @Override
    public List<EmployeeResponse> getEmployeesBySalaryGreaterThanEqual(BigDecimal salary) {
        log.info("fetching employees salary greater than zero salary{}", salary);

        List<Employee> employees=employeeRepository.findBySalaryGreaterThanEqual(salary);
        return employees.stream().map(employee -> new EmployeeResponse(
                        employee.getId(),
                        employee.getName(),
                        employee.getEmail(),
                        employee.getSalary(),
                        employee.getDepartment().getName(),
                        employee.getJoiningDate()))
                .toList();
    }

    @Override
    public Page<EmployeeResponse> searchEmployees(String department, String name, BigDecimal minSalary, int page, int size, String sortBy, String direction) {
        Specification<Employee> specification =
                (root, query, criteriaBuilder) -> null;
        if(department!=null && !department.isBlank()){
            specification=specification.and(EmployeeSpecification.hasDepartment(department));
        }
        if(name!=null && !name.isBlank()){
            specification=specification.and((EmployeeSpecification.nameContains(name)));
        }
        if(minSalary!=null){
            specification=specification.and(EmployeeSpecification.salaryGreaterThan(minSalary));
        }
        Sort sort;
        if(direction.equalsIgnoreCase("asc")){
            sort=Sort.by(sortBy).descending();
        }
        else{
            sort=Sort.by(sortBy).ascending();
        }
        Pageable pageable= PageRequest.of(page,size,sort);
        Page<Employee> employeePage=employeeRepository.findAll(specification,pageable);
        return employeePage.map(employee ->new EmployeeResponse(
                employee.getId(),
                employee.getName(),
                employee.getEmail(),
                employee.getSalary(),
                employee.getDepartment().getName(),
                employee.getJoiningDate()));
    }

    @Override
    @Transactional
    public EmployeeResponse promoteEmployee(Long id, Long departmentId, BigDecimal salary) {
        log.info("updating employee with id:{},departmentId:{},salary:{}", id,departmentId,salary);

        Employee employee=employeeRepository.findById(id).orElseThrow(() -> new ResourceNotfoundException("Employee not found with id:" +id));
        Department department=departmentRepository.findById(departmentId).orElseThrow(()-> new ResourceNotfoundException("department id is not found:" + departmentId));
        //hibernate uses dirty checking
        employee.setDepartment(department);
        employee.setSalary(salary);

        Employee updatedEmployee=employeeRepository.save(employee);
        log.info("updated successfuly employee with id:{},departmentId:{},salary:{}", updatedEmployee.getId(),updatedEmployee.getDepartment().getId(),updatedEmployee.getSalary());

        return new EmployeeResponse(
                updatedEmployee.getId(),
                updatedEmployee.getName(),
                updatedEmployee.getEmail(),
                updatedEmployee.getSalary(),
                updatedEmployee.getDepartment().getName(),
                updatedEmployee.getJoiningDate());

    }

    @Override
    public List<EmployeeBasicResponse> getBasicEmployeesByDepartment(String departmentName) {
        log.info("fetching employee with departmentname{}", departmentName);

        return employeeRepository.findBasicEmployeesByDepartment(departmentName);
    }

    @Override
    public Page<EmployeeSearchResponse> searchEmployees(Pageable pageable) {
        log.info("updating employee with pageable {}", pageable);

        return employeeRepository.searchEmployees(pageable);
    }

    @Override
    public EmployeeDetailsResponse getEmployeeDetails(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found with id: " + id));
        DepartmentResp departmentResp=departmentClient.getDepartmentById(Long.valueOf(employee.getDepartment().getId()

        ));
        return new EmployeeDetailsResponse(employee.getId(),
                employee.getName(),
                employee.getEmail(),
                employee.getSalary(),
                employee.getDepartment().getName(),
                employee.getJoiningDate(),
                departmentResp);


    }


}



