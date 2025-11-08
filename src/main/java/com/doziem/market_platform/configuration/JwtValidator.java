package com.doziem.market_platform.configuration;

import com.doziem.market_platform.model.User;
import com.doziem.market_platform.repository.UserRepository;
import com.doziem.market_platform.service.impl.UserPrincipal;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtValidator extends OncePerRequestFilter {

    private final UserRepository userRepository;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String jwt = request.getHeader(JwtConstant.JWT_HEADER);

        if(jwt !=null){
            // Validate the JWT token here (e.g., using a JWT library)

            jwt = jwt.substring(7);
            try {

                SecretKey key = Keys.hmacShaKeyFor(JwtConstant.JWT_SECRET.getBytes());
                Claims claims = Jwts.parser()
                        .verifyWith(key)
                        .build()
                        .parseSignedClaims(jwt)
                        .getPayload();

                String username = String.valueOf(claims.get("email"));

                String authorities = String.valueOf(claims.get("authorities"));
                List<GrantedAuthority> auth = AuthorityUtils.commaSeparatedStringToAuthorityList(authorities);


                User user = userRepository.findByEmail(username)
                        .orElseThrow(() -> new UsernameNotFoundException("User not found"));

                UserPrincipal userPrincipal = new UserPrincipal(user, auth);

                 Authentication authentication = new UsernamePasswordAuthenticationToken(userPrincipal, null, auth);

                 SecurityContextHolder.getContext().setAuthentication(authentication);

            } catch (JwtException | IllegalArgumentException e) {
                throw new BadCredentialsException("Invalid Jwt:::{}", e);
            }
        }
        // If the token is valid, proceed with the filter chain
        filterChain.doFilter(request, response);
    }
}
