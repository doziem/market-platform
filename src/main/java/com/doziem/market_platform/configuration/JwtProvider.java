package com.doziem.market_platform.configuration;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class JwtProvider {

    SecretKey key = Keys.hmacShaKeyFor(JwtConstant.JWT_SECRET.getBytes());

    public String generateToken( Authentication authentication) {
        String email = authentication.getName();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        String roles = populateAuthorities(authorities);

        return Jwts.builder()
                .issuedAt(Date.from((LocalDateTime.now()
                        .atZone(ZoneId.systemDefault())
                        .toInstant())))
                .expiration(Date.from(LocalDateTime.now().plusHours(24)
                        .atZone(ZoneId.systemDefault())
                        .toInstant()))
                .claim("email", email)
                .claim("authorities", roles)
                .signWith(key)
                .compact();
    }

    public String getEmailFromToken(String token) {
        token = token.substring(7);

//        Claims claims = Jwts.parser()
//                .verifyWith(key)
//                .build()
//                .parseSignedClaims(token)
//                .getPayload();
//        String email = String.valueOf(claims.get("email"));
//        return email;

        return String.valueOf(Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("email"));

    }

    private String populateAuthorities(Collection<? extends GrantedAuthority> authorities) {
        Set<String> auths = new HashSet<>();
        for (GrantedAuthority authority : authorities) {
            auths.add(authority.getAuthority());
        }
        return String.join(",", auths);
    }

}
