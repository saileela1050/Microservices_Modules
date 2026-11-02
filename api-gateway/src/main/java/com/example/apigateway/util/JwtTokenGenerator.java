package com.example.apigateway.util;

import com.nimbusds.jwt.JWTClaimsSet;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import java.time.Instant;
@Component
public class JwtTokenGenerator {
    private final JwtEncoder jwtEncoder;

    public JwtTokenGenerator(JwtEncoder jwtEncoder) {
        this.jwtEncoder = jwtEncoder;
    }
    public String generateToken(String username,String role){
        Instant now= Instant.now();
        JwtClaimsSet claims= JwtClaimsSet.builder().
                subject(username).issuedAt(now).expiresAt(now.plusSeconds(3600))
                .claim("role",role).build();
        JwsHeader header= JwsHeader.with(MacAlgorithm.HS256).build();
        return jwtEncoder.encode(JwtEncoderParameters.from(header,claims)).getTokenValue();
    }
}
