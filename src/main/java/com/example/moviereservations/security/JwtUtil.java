package com.example.moviereservations.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.*;
import java.util.function.Function;

@Component
public class JwtUtil {
    @Value("${app.jwt.secret:ZmFrZV9zdXBlcl9sb25nX3JhbmRvbV9zZWNyZXRfdXNlX2Vudl9mb3JfcmVhbA==}")
    private String secretBase64;

    @Value("${app.jwt.expiration-ms:86400000}")
    private long jwtExpirationMs;

    public String extractUsername (String token) {
        return extractClaim (token, Claims::getSubject);
    }

    public Date extractExpiration (String token) {
        return extractClaim (token, Claims::getExpiration);
    }

    public <T> T extractClaim (String token, Function <Claims, T> resolver) {
        Claims claims = parseAllClaims (token);
        return resolver.apply (claims);
    }

    public String generateToken (UserDetails userDetails) {
        Map <String, Object> claims = new HashMap<>();
        claims.put ("roles", userDetails.getAuthorities ().stream ().map (Object::toString).toList ());
        return createToken (claims, userDetails.getUsername ());
    }

    public boolean validateToken (String token, UserDetails userDetails) {
        String username = extractUsername (token);
        return username.equals (userDetails.getUsername ()) && !isTokenExpired (token);
    }

    private String createToken (Map <String, Object> claims, String subject) {
        Date now = new Date ();
        Date exp = new Date (now.getTime () + this.jwtExpirationMs);
        return Jwts.builder ().setClaims (claims).setSubject (subject).setIssuedAt (now)
                .setExpiration (exp).signWith (signingKey (), SignatureAlgorithm.HS256).compact ();
    }

    private boolean isTokenExpired (String token) {
        return extractExpiration (token).before (new Date ());
    }

    private Claims parseAllClaims (String token) {
        return Jwts.parserBuilder ().setSigningKey (signingKey ())
                .build ().parseClaimsJws (token).getBody ();
    }

    private Key signingKey () {
        byte [] keyBytes = Decoders.BASE64.decode (this.secretBase64);
        return Keys.hmacShaKeyFor (keyBytes);
    }
}