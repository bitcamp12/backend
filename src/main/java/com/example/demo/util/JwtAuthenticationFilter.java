package com.example.demo.util;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.demo.service.CustomUserDetailsService;

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
    
    private final CustomUserDetailsService customUserDetailsService;

    public JwtAuthenticationFilter(JWTUtil jwtUtil, AuthenticationManager authenticationManager, CustomUserDetailsService customUserDetailsService) {
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
		this.customUserDetailsService = customUserDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String token = extractToken(request);
       // System.out.println("액세토큰 검증 중: " + token); // 토큰 출력       
       // String cookie =extractRefreshToken(request);
       // System.out.println("쿠키쿠키"+cookie);

        if (token != null) {
            processTokenAuthentication(token, request, response);
        }

        // 필터 체인에 요청 전달
        filterChain.doFilter(request, response);
    }

    // 토큰 처리 메서드
    private void processTokenAuthentication(String token, HttpServletRequest request, HttpServletResponse response) {
        boolean isExpired = jwtUtil.isExpired(token);
       // System.out.println("토큰 만료 여부: " + isExpired); // 만료 여부 출력

        if (!isExpired) {
        	//System.out.println("(\"authenticateWithToken start\");");
            authenticateWithToken(token);
        } else {
        	//System.out.println("(\"handleExpiredToken start\");");
            handleExpiredToken(request, response);
        }
    }

    // 유효한 토큰으로 인증 처리
    private void authenticateWithToken(String token) {
        String username = jwtUtil.getUsername(token);
       // System.out.println("토큰에서 추출된 사용자 이름: " + username);
        
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
        
        if (userDetails != null) {
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);  // SecurityContext에 인증 정보 설정
        }


      //  System.out.println("유효한 토큰, 인증 완료.");
    }

    // 만료된 토큰을 처리하고, 리프레시 토큰이 유효하면 새 액세스 토큰을 발급
    private void handleExpiredToken(HttpServletRequest request, HttpServletResponse response) {
        String refreshToken = extractRefreshToken(request);
        if (refreshToken != null && !jwtUtil.isExpired(refreshToken)) {
          //  System.out.println("유효한 리프레시 토큰 존재");

            String username = jwtUtil.getUsername(refreshToken);
            String role = jwtUtil.getRole(refreshToken);
            String newAccessToken = jwtUtil.createJwt(username, role, 60 * 1000 * 10L); // 10분 유효
           // System.out.println("새로운 액세스 토큰 생성: " + newAccessToken);

            response.setStatus(200);
            response.addHeader("Authorization", "Bearer " + newAccessToken);
        } else {
            System.out.println("리프레시 토큰이 없거나 만료됨.");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            try {
                response.getWriter().write("토큰이 만료되었습니다. 다시 로그인하세요.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Authorization 헤더에서 JWT 토큰을 추출하는 메서드
    private String extractToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        //System.out.println("헤더 값 추출 중JWT: " + header);

        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7); // "Bearer " 이후의 토큰 반환
        }

        return null;
    }

    // 리프레시 토큰을 쿠키에서 추출하는 메서드
    private String extractRefreshToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        //System.out.println("리프레쉬 쿠키 값 추출 중JWT: " + cookies);
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("refreshToken".equals(cookie.getName())) {
                    System.out.println("리프레시 토큰 찾음: " + cookie.getValue());
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}
