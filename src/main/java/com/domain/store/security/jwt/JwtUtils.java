package com.domain.store.security.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtils {

    @Value("${jwt.token.jwtSecret}")
    private String secretKey;

    @Value("${jwt.token.expirationInMils}")
    private int expirationTime;

    public String getBearerToken(HttpServletRequest request){
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")){
            return bearerToken.substring(7);
        }else{
            return null;
        }
    }

    public String generateToken(UserDetails userDetails){
        String userName = userDetails.getUsername();
        return Jwts.builder()
                .setSubject(userName)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + expirationTime))
                .signWith(key())
                .compact();
    }

    private Key key(){
        return Keys.hmacShaKeyFor(Encoders.BASE64.encode(secretKey.getBytes()).getBytes());
    }

    public String getUserNameFromJwtToken(String token){
        return Jwts.parserBuilder()
                .setSigningKey(key())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateJwtToken(String token){
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException|UnsupportedJwtException|MalformedJwtException|SignatureException|IllegalArgumentException e) {
            throw new RuntimeException(e);
        }
    }
}
