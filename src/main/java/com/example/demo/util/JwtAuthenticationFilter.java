package com.example.demo.util;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public JwtAuthenticationFilter(JWTUtil jwtUtil, AuthenticationManager authenticationManager) {
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // Authorization 헤더에서 JWT 토큰 추출
        String token = getTokenFromRequest(request);
        System.out.println("액세토큰Token: " + token); // 토큰 출력

        // 토큰이 존재하고, 유효한 경우
        if (token != null) {
            boolean isExpired = jwtUtil.isExpired(token);
            System.out.println("Is token expired: " + isExpired); // 만료 여부 출력

            if (!isExpired) {
                String username = jwtUtil.getUsername(token);
                System.out.println("Username from token: " + username); // 사용자 이름 출력

                // 인증을 위해 UsernamePasswordAuthenticationToken 객체 생성
                Authentication authentication = new UsernamePasswordAuthenticationToken(username, null, null);

                // 인증된 사용자를 SecurityContext에 설정
                SecurityContextHolder.getContext().setAuthentication(authentication);
                System.out.println("토큰이 유효하고 만료되지 않았습니다.");
            } else {
                System.out.println("토큰이 만료되었습니다.");

                // 만료된 토큰이라면 refresh token을 사용하여 새로운 액세스 토큰 발급
                String refreshToken = getRefreshTokenFromRequest(request);
                if (refreshToken != null && !jwtUtil.isExpired(refreshToken)) {
                    System.out.println("유효한 리프레시 토큰이 존재합니다.");

                    String username = jwtUtil.getUsername(refreshToken);
                    String role = jwtUtil.getRole(refreshToken);

                    // 새로운 액세스 토큰 생성
                    String newAccessToken = jwtUtil.createJwt(username, role, 60 * 1000 * 10L); // 10분 유효
                    System.out.println("새로운 액세스 토큰 생성: " + newAccessToken);

                    // 새로운 액세스 토큰 발급
                    response.setStatus(200);
                    response.addHeader("Authorization", "Bearer " + newAccessToken);

                    // 새로운 리프레시 토큰 유지 (필요 시 재발급 가능)
                } else {
                    System.out.println("리프레시 토큰이 없거나 만료되었습니다.");
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.getWriter().write("Token expired. Please login again.");
                    return;
                }
            }
        }

        // 필터 체인에 요청 전달
        filterChain.doFilter(request, response);
    }

    // Authorization 헤더에서 JWT 토큰을 추출하는 메서드
    private String getTokenFromRequest(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        System.out.println("헤더값"+header);
        if (header != null && header.startsWith("Bearer ")) {
        	System.out.println("액세스토큰추출");
            return header.substring(7); // "Bearer " 이후의 토큰 반환
        }
        System.out.println("액세스토큰없음");
        return null;
    }

    // 클라이언트에서 리프레시 토큰을 쿠키에서 추출하는 메서드
    private String getRefreshTokenFromRequest(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("refreshToken".equals(cookie.getName())) {
                    System.out.println("리프레시 토큰을 찾았습니다: " + cookie.getValue());
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}
