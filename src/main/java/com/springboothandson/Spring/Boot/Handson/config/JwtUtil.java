package com.springboothandson.Spring.Boot.Handson.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.*;
import java.util.function.Function;


@Service
public class JwtUtil {

    @Value("${security.jwt.secret-key}")
    private String SECRET_KEY;

    @Value("${security.jwt.expiration}")
    private long EXPIRATION;

    public String extractUsername(String jwtToken){
        return extractClaims(jwtToken, Claims::getSubject);
    }

    public Date extractExpiration(String jwtToken){
        return extractClaims(jwtToken, Claims::getExpiration);
    }


    private <T> T extractClaims(String jwtToken, Function<Claims, T> claimResolver){
        final Claims claims = extractAllClaims(jwtToken);
        return claimResolver.apply(claims);
    }

    private Claims extractAllClaims(String jwtToken) {
        return Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(jwtToken)
                .getBody();
    }

    private Key getKey() {
        byte[] bytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(bytes);
    }

    public String generateToken(UserDetails userDetails){
        Map<String, Object> claims = new HashMap<>();
        return createToken(userDetails,claims);
    }

    private String createToken(UserDetails userDetails, Map<String, Object> claims) {
        Calendar calendar = Calendar.getInstance();
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(calendar.getTime())
                .setExpiration(new Date(calendar.getTimeInMillis() + EXPIRATION))
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validateToken(String jwtToken, UserDetails userDetails){
        return (userDetails.getUsername().equals(extractUsername(jwtToken))) && !isTokenExpired(jwtToken);
    }

    public boolean isTokenExpired(String jwtToken){
        return extractExpiration(jwtToken).before(new Date());
    }

}
