package com.example.gameserver.service;

import com.example.gameserver.model.MyUserDetails;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;

@Service
public class TokenService {

    private final JwtEncoder encoder;

    public TokenService(JwtEncoder encoder) {
        this.encoder = encoder;
    }

    public String generateToken(MyUserDetails myUserDetails) {
        Instant now = Instant.now();
        String scope = myUserDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plus(1, ChronoUnit.DAYS))
                .subject(myUserDetails.getUsername())
                .claim("scope", scope)
                .build();
        JwsHeader jwsHeader = JwsHeader.with(() -> "HS256").build();
        return encoder.encode(JwtEncoderParameters.from(jwsHeader, claims)).getTokenValue();
    }
}
