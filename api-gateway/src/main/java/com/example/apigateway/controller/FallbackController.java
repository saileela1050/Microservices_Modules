package com.example.apigateway.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class FallbackController {
    @GetMapping("/fallback/employee")
    public Mono<ResponseEntity<String>> fallbackEmployee(){
        return Mono.just(ResponseEntity
                .status(503).body("EMployee Service is currently unavailable:Please try again later.\""));
    }


}
