package com.example.employeemanagement.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ExternalUserRequest {
    private String name;
    private String username;
    private String email;
}
