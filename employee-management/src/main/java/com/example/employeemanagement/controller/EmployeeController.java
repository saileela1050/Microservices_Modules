package com.example.employeemanagement.controller;

import com.example.employeemanagement.client.DepartmentClient;
import com.example.employeemanagement.dto.*;
import com.example.employeemanagement.entity.Employee;
import com.example.employeemanagement.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.List;


@Slf4j
@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private DepartmentClient departmentClient;

    @Value("${server.port}")
    private String port;

//    @Value("${employee.message}")
//    private String employeeMessage;

    @PostMapping
    public ResponseEntity<EmployeeResponse> createEmployee(@RequestBody @Valid EmployeeCreateRequest request){
       EmployeeResponse response=employeeService.createEmployee(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @GetMapping
    public ResponseEntity<Page<EmployeeResponse>> getAllEmployees(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction){
        Page<EmployeeResponse> response=employeeService.getAllEmployees(page, size, sortBy, direction);
        return ResponseEntity.ok(response);
    }


    @GetMapping("/search/department")
    public ResponseEntity<List<EmployeeResponse>> getEmployeesByDepartment(@RequestParam String department)
    {
        List<EmployeeResponse> response=employeeService.getEmployeesByDepartment(department);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/search/name")
    public ResponseEntity<List<EmployeeResponse>> getEmployeesByName(@RequestParam String name){
        List<EmployeeResponse> response=employeeService.getEmployeesByName(name);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/search/salary")
    public ResponseEntity<List<EmployeeResponse>> getEmployeesBySalaryGreaterThanEqual(@RequestParam BigDecimal salary){
        List<EmployeeResponse> response=employeeService.getEmployeesBySalaryGreaterThanEqual(salary);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponse> getEmployeeById(@PathVariable Long id){
       EmployeeResponse response=employeeService.getEmployeeById(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeResponse> updatEmployeeById( @PathVariable Long id, @RequestBody @Valid EmployeeUpdateRequest employeeUpdateRequest){
        EmployeeResponse response=employeeService.updateEmployee(id,employeeUpdateRequest);
        return ResponseEntity.ok(response);
    }


    @DeleteMapping("/{id}")
    public  ResponseEntity<Void> deleteEmployeeById(@PathVariable Long id){
         employeeService.deleteEmployee(id);
         return ResponseEntity.noContent().build();
    }


    @PatchMapping("/{id}")
    public ResponseEntity<EmployeeResponse> patchEmployee(@PathVariable Long id,@RequestBody EmployeePatchRequest employeePatchRequest){
        EmployeeResponse response=employeeService.patchEmployee(id,employeePatchRequest);
        return  ResponseEntity.ok(response);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<EmployeeResponse>> searchEmployees(
            @RequestParam(required = false)
            String department,
            @RequestParam(required = false)
            String name,
            @RequestParam(required = false)
            BigDecimal minSalary,
            @RequestParam(defaultValue = "0")
            int page,
            @RequestParam(defaultValue = "10")
            int size,
            @RequestParam(defaultValue = "id")
            String sortBy,
            @RequestParam(defaultValue = "asc")
            String direction) {
        Page<EmployeeResponse> response=employeeService.searchEmployees(department, name, minSalary, page, size, sortBy, direction);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/promote")
    public ResponseEntity<EmployeeResponse> promoteEmployee(@PathVariable Long id,
                                            @RequestParam Long department,
                                            @RequestParam BigDecimal salary){
        EmployeeResponse response=employeeService.promoteEmployee(id, department, salary);
        return ResponseEntity.ok(response);
    }

//@GetMapping("/basic")
//    public List <EmployeeBasicResponse> getBasicEmployeeDetails(){
//        return employeeService.getBasicEmployeeDetails();
//}
    @GetMapping("/basic/department")
    public ResponseEntity<List<EmployeeBasicResponse>> getBasicEmployeesByDepartment(
            @RequestParam String departmentName) {

       List<EmployeeBasicResponse> response= employeeService.getBasicEmployeesByDepartment(departmentName);
        return ResponseEntity.ok(response);

    }

    @GetMapping("/search/se")
    public ResponseEntity<Page<EmployeeSearchResponse>> searchEmployees(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<EmployeeSearchResponse> response=employeeService.searchEmployees(pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/departments/{id}")
    public DepartmentResp getDepartment(@PathVariable Long id) {
        return departmentClient.getDepartmentById(id);
    }

    @GetMapping("/{id}/details")
    public EmployeeDetailsResponse getEmployeDetails(@PathVariable Long id){
        return employeeService.getEmployeeDetails(id);
    }

//    @GetMapping("/{id}/gateway")
//    public EmployeeResponse getEmployeeById(@PathVariable Long id,
//            @RequestHeader(value = "X-Gateway-Request", required = false) String gatewayRequest) {
//        log.info("X-Gateway-Request = {}", gatewayRequest);
//        return employeeService.getEmployeeById(id);
//    }
    @GetMapping("/{id}/gateway")
    public EmployeeResponse getEmployeeById(@PathVariable Long id,
            @RequestHeader(value = "X-Request-Id", required = false) String requestId) {
        log.info("REQUEST ID :" + requestId);
        return employeeService.getEmployeeById(id);
    }

//    @GetMapping("/instance")
//    public String getInstance(){
//        return "Employee Service running on port:" + port;
//    }

//    @GetMapping("/config-test")
//    public String configTest(){
//        return employeeMessage;
//    }
@GetMapping("/slow")
public Mono<String> slowResponse() {

    return Mono.delay(Duration.ofSeconds(10)).thenReturn("Employee response after 10 seconds");
}
}
