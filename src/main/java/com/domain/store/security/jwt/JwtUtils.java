package com.domain.store.security.jwt;

import com.domain.store.security.user.ShopUserDetails;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.List;

@Component
@NoArgsConstructor
public class JwtUtils {

    @Value("${auth.token.jwtSecret}")
    private String jwtSecret;

    @Value("${auth.token.expirationInMils}")
    private int ExpirationTime;

    public String generateToken(Authentication authentication){
        ShopUserDetails shopUserDetails = (ShopUserDetails) authentication.getPrincipal();
        List<String> roles = shopUserDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();

        return Jwts.builder().setSubject(shopUserDetails.getEmail())
                .claim("id", shopUserDetails.getId())
                .claim("roles", roles)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + ExpirationTime))
                .signWith(secretKey(), SignatureAlgorithm.HS256 )
                .compact();
    }

    private Key secretKey(){
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    public Boolean validateToken(String token){
        try {
            Jwts.parserBuilder().setSigningKey(secretKey())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | SignatureException | IllegalArgumentException e) {
            throw new JwtException(e.getMessage());
        }
    }

    public String getUserNameFromToken(String token){
        return Jwts.parserBuilder().setSigningKey(secretKey())
                .build().parseClaimsJws(token).getBody().getSubject();
    }
}











