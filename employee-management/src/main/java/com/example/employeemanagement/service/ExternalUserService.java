package com.example.employeemanagement.service;

import com.example.employeemanagement.dto.ExternalUserRequest;
import com.example.employeemanagement.dto.ExternalUserResponse;

public interface ExternalUserService {
    ExternalUserResponse getUserById(Long id);
    ExternalUserResponse createUser(ExternalUserRequest request);
}
