package com.example.jungle_board.util.provider;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.ArrayList;
import java.util.Date;

import static javax.crypto.Cipher.SECRET_KEY;

@Component
public class JwtTokenProvider {
    @Value("${JWT_SECRET}")
    private String secretKey;
    @Value("${JWT_EXPIRATION}")
    private long tokenValidityInSeconds;

    // JWT 생성
    public String generateToken(Authentication authentication) {
        User user = (User) authentication.getPrincipal();

        Key key = Keys.hmacShaKeyFor(secretKey.getBytes());

        return Jwts.builder()
                .setSubject(user.getUsername()) // 사용자 이름 저장
                .setIssuedAt(new Date()) // 발행 시간
                .setExpiration(new Date(System.currentTimeMillis() + tokenValidityInSeconds)) // 만료 시간
                .signWith(key) // 서명 키 설정
                .compact(); // 토큰 생성
    }

    // JWT에서 사용자 이름 추출
    public String getUsernameFromToken(String token) {
        Key key = Keys.hmacShaKeyFor(secretKey.getBytes());

        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key) // 서명 키 설정
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject(); // 사용자 이름 반환
    }

    public Authentication getAuthentication(String token) {
        return new UsernamePasswordAuthenticationToken(getUsernameFromToken(token), null, new ArrayList<>());
    }

    // JWT 유효성 검증
    public boolean validateToken(String token) {
        try {
            Key key = Keys.hmacShaKeyFor(secretKey.getBytes());
            Jwts.parserBuilder()
                    .setSigningKey(key) // 서명 키 설정
                    .build()
                    .parseClaimsJws(token); // 토큰 검증
            return true; // 유효한 경우 true 반환
        } catch (Exception e) {
            return false; // 유효하지 않은 경우 false 반환
        }
    }
}
