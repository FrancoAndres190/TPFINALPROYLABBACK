package com.gymapp.gym.security;

import com.gymapp.gym.service.UsrService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class TokenUtils {

    @Autowired
    UsrService usrService;

    @Value("${jwt.secret.key}")
    private String ACCESS_TOKEN_SECRET;
    @Value("${jwt.time.expiration}")
    private Long ACCESS_TOKEN_VALIDITY_MILISECONDS;

    public String createToken(String nombre, String email, Set<GrantedAuthority> authorities){

        Date expirationDate = new Date(System.currentTimeMillis() + ACCESS_TOKEN_VALIDITY_MILISECONDS);

        Map<String, Object> claims = new HashMap<>();

        claims.put("nombre", nombre);
        claims.put("roles", authorities.stream()
                .map(GrantedAuthority::getAuthority).collect(Collectors.toSet()));

        return Jwts.builder()
                .setSubject(email)
                .setExpiration(expirationDate)
                .addClaims(claims)
                .signWith((Keys.hmacShaKeyFor(ACCESS_TOKEN_SECRET.getBytes())))
                .compact();

    }

    public UsernamePasswordAuthenticationToken getAuthentication(String token) {

        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(ACCESS_TOKEN_SECRET.getBytes())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            String email = claims.getSubject();

            List<String> roles = claims.get("roles", List.class);

            return new UsernamePasswordAuthenticationToken
                    (email, null, roles.stream().map(SimpleGrantedAuthority::new)
                            .collect(Collectors.toList()));

        }catch (JwtException e) {
            System.out.println(e.getMessage());
            return null;
        }

    }
    

}
