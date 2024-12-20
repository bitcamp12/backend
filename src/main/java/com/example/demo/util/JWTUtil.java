package com.example.demo.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.List;

@Component
public class JWTUtil {

    private final Key key;

    public JWTUtil(@Value("${spring.jwt.secret}") String secret) {
        byte[] byteSecretKey = Decoders.BASE64.decode(secret);
        key = Keys.hmacShaKeyFor(byteSecretKey);
    }

    public String getUsername(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().get("username", String.class);
    }

    public String getRole(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().get("role", String.class);
    }

    public Boolean isExpired(String token) {
        try {
            // JWT 파싱 후 만료 시간 확인
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getExpiration().before(new Date());
        } catch (ExpiredJwtException e) {
            // 만료된 토큰인 경우 true 반환
            System.out.println("만료된 토큰: " + e.getMessage());
            return true;
        } catch (Exception e) {
            // 다른 예외 처리
            System.out.println("토큰 처리 중 오류 발생: " + e.getMessage());
            return true; // 예외 발생 시 true 반환 (만료된 토큰으로 처리)
        }
    }

    public String createJwt(String username, String role, Long expiredMs) {
        Claims claims = Jwts.claims();
        claims.put("username", username);
        claims.put("role", role);
        

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiredMs))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }
    
    
    // 리프레쉬 토큰용  기존거랑 똑같음 
    public String createRefreshToken(String username, String role, Long expiredMs) {
        Claims claims = Jwts.claims();
        claims.put("username", username);
        claims.put("role", role);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiredMs))  // 예시로 10초의 유효 시간 설정
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    
    
    

    // 토큰 검증 메서드
    public boolean validateToken(String token) {
        try {

            // 토큰을 파싱하여 유효성 검사
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true; // 유효한 토큰

        } catch (ExpiredJwtException e) {
            System.err.println("토큰이 만료되었습니다: " + e.getMessage());
        } catch (JwtException | IllegalArgumentException e) {
            System.err.println("유효하지 않은 토큰입니다: " + e.getMessage());
        }
        return false; // 유효하지 않은 토큰
    }
    


}
