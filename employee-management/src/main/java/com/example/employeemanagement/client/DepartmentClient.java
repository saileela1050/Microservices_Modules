package com.example.employeemanagement.client;

import com.example.employeemanagement.dto.DepartmentResp;
import com.example.employeemanagement.exception.DepartmentServiceException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;


@Slf4j
@Component
public class DepartmentClient {

//    @Autowired
//    private WebClient webClient;
private final WebClient departmentWebClient;

    public DepartmentClient( @Qualifier("departmentWebClient") WebClient departmentWebClient) {
        this.departmentWebClient = departmentWebClient;
    }

    @Retry(name="departmentServiceRegistry")
    @CircuitBreaker(name = "departmentService", fallbackMethod = "departmentServiceFallback")
    public DepartmentResp getDepartmentById(Long id) {
        log.info("Calling Department Service for department id: {}", id);

            return departmentWebClient
                    .get()
                      .uri("/departments/{id}", id).retrieve()
                    .bodyToMono(DepartmentResp.class)
                    .block();

    }
    public DepartmentResp departmentServiceFallback(Long id, Throwable throwable) {

        throw new DepartmentServiceException("Department service is currently unavailable", throwable);
    }
}
