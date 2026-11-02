package com.example.apigateway.config;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import reactor.core.publisher.Mono;

import java.util.List;

public class JwtAuthConverter implements Converter<Jwt, Mono<AbstractAuthenticationToken>> {
    @Override
    public Mono<AbstractAuthenticationToken> convert(Jwt jwt) {
        String role=jwt.getClaimAsString("role");
        SimpleGrantedAuthority authority=new SimpleGrantedAuthority("ROLE_" +role);

        AbstractAuthenticationToken authentication = new JwtAuthenticationToken(jwt, List.of(authority), jwt.getSubject());
        return Mono.just(authentication);
    }


}
