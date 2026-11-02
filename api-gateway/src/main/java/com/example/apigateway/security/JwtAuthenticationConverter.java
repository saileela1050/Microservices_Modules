package com.example.apigateway.security;

import org.springframework.cglib.core.AbstractClassGenerator;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.core.convert.converter.Converter;

import java.util.List;

@Component
public class JwtAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    @Override
    public AbstractAuthenticationToken convert(Jwt jwt) {
        String role = jwt.getClaimAsString("role");
        SimpleGrantedAuthority authority =
                new SimpleGrantedAuthority("ROLE_" + role);
        return new JwtAuthenticationToken(jwt, List.of(authority)
        );
    }

}
