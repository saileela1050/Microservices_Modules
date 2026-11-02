package com.example.apigateway.config;

import com.example.apigateway.security.JwtAuthenticationConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.web.server.SecurityWebFilterChain;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig {


    @Value("${jwt.secret}")
    private String jwtSecret;

    private final JwtAuthenticationConverter jwtAuthenticationConverter;

    public SecurityConfig(JwtAuthenticationConverter jwtAuthenticationConverter) {
        this.jwtAuthenticationConverter = jwtAuthenticationConverter;
    }

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http){
        return http.
                csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(exchange->exchange
                                .pathMatchers(org.springframework.http.HttpMethod.DELETE,
                                        "/employees/**").hasRole("ADMIN")
                        .pathMatchers("/actuator/health","/auth/token").permitAll()
                        .pathMatchers("/employees/**").hasAnyRole("USER", "ADMIN")
                        .pathMatchers("/departments/**").hasAnyRole("USER", "ADMIN")
                        .anyExchange().authenticated())
        .oauth2ResourceServer(
                oauth2 -> oauth2.jwt(jwt->jwt.jwtAuthenticationConverter(new JwtAuthConverter()))).build();

    }

    @Bean
    public ReactiveJwtDecoder jwtDecoder(){
        SecretKeySpec secretKey=new SecretKeySpec(jwtSecret.getBytes(StandardCharsets.UTF_8),"HmacSHA256");
        return NimbusReactiveJwtDecoder.withSecretKey(secretKey).build();
    }

    @Bean
    public JwtEncoder jwtEncoder(){
        SecretKeySpec secretKey=new SecretKeySpec(jwtSecret.getBytes(StandardCharsets.UTF_8),"HmacSHA256");
        return NimbusJwtEncoder.withSecretKey(secretKey).build();
    }
}
