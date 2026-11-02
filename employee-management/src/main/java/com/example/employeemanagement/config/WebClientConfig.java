package com.example.employeemanagement.config;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Configuration
public class WebClientConfig {

    @Value("${external.user-service.connect-timeout}")
    private String connectTimeout;

//    @Value("${external.user-service.base-url}")
//    private String url;

    @Value("${external.user-service.response-timeout}")
    private String responseTimeout;

    @Bean
    public WebClient departmentWebClient( @Value("${department-service.base-url}") String baseUrl) {
        int connectTimeoutMillis = Integer.parseInt(connectTimeout);
        int responseTimeoutSeconds = Integer.parseInt(responseTimeout);

        HttpClient httpClient = HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS,connectTimeoutMillis)
                .responseTimeout(Duration.ofSeconds(responseTimeoutSeconds))
                .doOnConnected(connection -> connection
                        .addHandlerLast(new ReadTimeoutHandler(responseTimeoutSeconds, TimeUnit.SECONDS))
                        .addHandlerLast(new WriteTimeoutHandler(responseTimeoutSeconds, TimeUnit.SECONDS)));


        return WebClient.builder()
                .baseUrl(baseUrl)
                .clientConnector(
                        new ReactorClientHttpConnector(httpClient)
                )
                .build();
    }
}