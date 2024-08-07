package com.bigdecimal.springbootbackend4nextjs.security.service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;

@RequiredArgsConstructor
public class JWTService {

  private final JwtEncoder jwtEncoder;

  public String accessToken(Authentication authentication) {
    Instant now = Instant.now();
    String scope = authentication
      .getAuthorities()
      .stream()
      .map(GrantedAuthority::getAuthority)
      .collect(Collectors.joining(" "));
    String token = null;
    JwtClaimsSet claimSet = JwtClaimsSet
      .builder()
      .issuedAt(now)
      .expiresAt(now.plus(1, ChronoUnit.DAYS))
      .subject(authentication.getName())
      .claim("scope", scope)
      .build();
    token =
      jwtEncoder.encode(JwtEncoderParameters.from(claimSet)).getTokenValue();
    return token;
  }
}
