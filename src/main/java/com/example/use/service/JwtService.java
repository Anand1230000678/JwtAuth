package com.example.use.service;

import com.example.use.entity.Users;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;
@Service
public class JwtService {
    private final String Private_Key="4bb6d1dfbafb64a681139d1586b6f1160d18159afd57c8c79136d7490630407c";




    public <T> T extractClaim(String token, Function<Claims, T> resolver) {
        Claims claims = extractAllClaims(token);
        return resolver.apply(claims);
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }
    public boolean isValid(String token, UserDetails user) {
        String username = extractUsername(token);
        return (username.equals(user.getUsername())) && !isTokenExpired(token);


    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }


    private Claims extractAllClaims(String token) {
        return
                Jwts.parserBuilder()
                        .setSigningKey(getSigninKey())
                        .build()
                        .parseClaimsJws(token)
                        .getBody();
    }

    public  String generateToken(Users user) {
        String token = Jwts
                .builder()
                .setSubject(user.getEmail())

                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 24*60*60*1000 ))
                .signWith(getSigninKey())
                .compact();

        return token;
    }

    private Key getSigninKey() {
        byte[] keyBytes = Decoders.BASE64URL.decode(Private_Key);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
