package com.example.employeemanagement.service;

import com.example.employeemanagement.dto.ExternalUserRequest;
import com.example.employeemanagement.dto.ExternalUserResponse;
import com.example.employeemanagement.exception.ExternalServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.UUID;
@Slf4j
@Service
public class ExternalUserServiceImpl implements ExternalUserService{

//    @Autowired
//    private WebClient webClient;
private final WebClient webClient;

    public ExternalUserServiceImpl(WebClient webClient) {
        this.webClient = webClient;
    }
    @Override
    public ExternalUserResponse getUserById(Long id) {

        return webClient.get().uri("https://jsonplaceholder.typicode.com/users/{id}",id).retrieve()
                .bodyToMono(ExternalUserResponse.class).block();

    }

    @Override
    public ExternalUserResponse createUser(ExternalUserRequest request) {
        String requestId = UUID.randomUUID().toString();
        log.info("Calling external user API with requestId: {}", requestId);

        try {
            return webClient.post().uri("/users").
                    header("Content-Type", "application/json").
                    header("Accept", "application/json").
                    header("X-Client-Id", "employee-management").
                    header("X-Request-Id", "requestId").
                    bodyValue(request).retrieve().
                    onStatus(status -> status.is4xxClientError(),
                            response -> response.bodyToMono(String.class).
                                    map(body -> new ExternalServiceException("Externalservice returned 4xx:" + body))).
                    onStatus(status -> status.is5xxServerError(),
                            response -> response.bodyToMono(String.class).map(body ->
                                    new ExternalServiceException("Externalservice returned 5xx:" + body)))


                    .bodyToMono(ExternalUserResponse.class).block();
        }
        catch (Exception e){
            log.error("Error while calling external user srevice ,requestId{}",requestId,e);
        }
        throw new ExternalServiceException("External user service is unavailable");
    }
}
