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


    //Metodo qe genera el token
    public String createToken(Long userID, String nombre, String email, Set<GrantedAuthority> authorities){

        //Duraciond del token
        Date expirationDate = new Date(System.currentTimeMillis() + ACCESS_TOKEN_VALIDITY_MILISECONDS);

        //Colocamos las claims en el token
        Map<String, Object> claims = new HashMap<>();
        claims.put("userID", userID);
        claims.put("nombre", nombre);
        claims.put("roles", authorities.stream()
                .map(GrantedAuthority::getAuthority).collect(Collectors.toSet()));

        //Devolvemos el token
        return Jwts.builder()
                .setSubject(email)
                .setExpiration(expirationDate)
                .addClaims(claims)
                .signWith((Keys.hmacShaKeyFor(ACCESS_TOKEN_SECRET.getBytes())))
                .compact();

    }

    //Metodo para corroborar el token
    public UsernamePasswordAuthenticationToken getAuthentication(String token) {

        try {

            //Obtenemos las claims
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(ACCESS_TOKEN_SECRET.getBytes())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            //Obtenemos el email
            String email = claims.getSubject();

            //Obtenemos los roles
            List<String> roles = claims.get("roles", List.class);

            Long userID = Long.parseLong(claims.get("userID").toString());

            //Devolvemos...
            return new UsernamePasswordAuthenticationToken
                    (userID, null, roles.stream().map(SimpleGrantedAuthority::new)
                            .collect(Collectors.toList()));

        }catch (JwtException e) {
            System.out.println(e.getMessage());
            return null;
        }

    }
    

}
