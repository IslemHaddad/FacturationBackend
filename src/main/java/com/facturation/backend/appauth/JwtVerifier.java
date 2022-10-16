package com.facturation.backend.appauth;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtVerifier extends OncePerRequestFilter {
    @SuppressWarnings("unchecked")
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");
        if(authorizationHeader.isEmpty() || authorizationHeader == null || !authorizationHeader.startsWith("Bearer")){
            filterChain.doFilter(request, response);
            return;
        }
        String token = authorizationHeader.replace("Bearer", "");
        try {
            Jws<Claims> jwsClaims = Jwts.parser()
                    .setSigningKey(Keys.hmacShaKeyFor("SecuritySchoolAppKeySecuritySchoolAppKeySecuritySchoolAppKeySecuritySchoolAppKeySecuritySchoolAppKey".getBytes()))
                    .parseClaimsJws(token);
            Claims body = jwsClaims.getBody();
            String username = body.getSubject();
            List<Map<String,String>> authorities = (List<Map<String,String>>) body.get("authorities");
            Set<SimpleGrantedAuthority> simpleGrantedAuthorities = authorities
            .stream().map(a -> new SimpleGrantedAuthority(a.get("authority")))
                .collect(Collectors.toSet());
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                username, 
                null,
                simpleGrantedAuthorities
                );
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }   catch (JwtException e){
            throw new IllegalStateException(String.format("Bearer %s cannot be trusted", token));
        }
        filterChain.doFilter(request, response);
    }
}
