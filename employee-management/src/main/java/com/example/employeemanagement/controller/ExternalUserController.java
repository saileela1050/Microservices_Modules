package com.example.employeemanagement.controller;


import com.example.employeemanagement.dto.ExternalUserRequest;
import com.example.employeemanagement.dto.ExternalUserResponse;
import com.example.employeemanagement.service.ExternalUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/external-users")
public class ExternalUserController {

    @Autowired
    private ExternalUserService externalUserService;

    @GetMapping("/{id}")
public ExternalUserResponse getUserById(@PathVariable Long id){
        return externalUserService.getUserById(id);

    }

    @PostMapping
    public ExternalUserResponse createUser(@RequestBody ExternalUserRequest request){
        return externalUserService.createUser(request);
    }
}
