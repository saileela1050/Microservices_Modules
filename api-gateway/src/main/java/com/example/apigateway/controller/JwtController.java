package com.example.apigateway.controller;

import com.example.apigateway.util.JwtTokenGenerator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JwtController {
    private final JwtTokenGenerator jwtTokenGenerator;

    public JwtController(JwtTokenGenerator jwtTokenGenerator) {
        this.jwtTokenGenerator = jwtTokenGenerator;
    }
    @GetMapping("/auth/token")
    public String generateToken(@RequestParam String username,@RequestParam String role){
        return jwtTokenGenerator.generateToken(username,role);

    }
}
