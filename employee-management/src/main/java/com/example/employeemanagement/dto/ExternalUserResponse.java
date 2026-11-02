package com.example.employeemanagement.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ExternalUserResponse {
    private Long id;
    private String name;
    private String username;
    private String email;


}
