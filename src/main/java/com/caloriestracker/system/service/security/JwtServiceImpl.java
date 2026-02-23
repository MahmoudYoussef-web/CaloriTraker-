package com.caloriestracker.system.service.security;
import com.caloriestracker.system.entity.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Service
public class JwtServiceImpl implements JwtService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration-ms:86400000}") // default 24h
    private long expiration;

    private Key key;


    @PostConstruct
    private void init() {

        this.key = Keys.hmacShaKeyFor(
                secret.getBytes(StandardCharsets.UTF_8)
        );
    }


    @Override
    public String generate(User user) {

        Date now = new Date();
        Date expiry = new Date(now.getTime() + expiration);

        return Jwts.builder()
                .setSubject(user.getId().toString())
                .claim("username", user.getUsername())
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }


    @Override
    public Long extractUserId(String token) {

        Claims claims = parse(token);

        return Long.valueOf(claims.getSubject());
    }


    @Override
    public boolean validate(String token) {

        try {
            parse(token);
            return true;

        } catch (ExpiredJwtException e) {
            return false;

        } catch (MalformedJwtException e) {
            return false;

        } catch (UnsupportedJwtException e) {
            return false;

        } catch (IllegalArgumentException e) {
            return false;
        }
    }


    private Claims parse(String token) {

        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}