package com.example.demo.util;

import com.example.demo.dto.member.MemberDTO;
import com.example.demo.service.CustomUserDetails;
import com.example.demo.service.RedisService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    
	//JWTUtil 주입
	private final JWTUtil jwtUtil;
	
    private final RedisService redisService;

    public LoginFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil,RedisService redisService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
		this.redisService = redisService;
        
        // 로그인 처리 URL 설정 설정 안하면 기본적으로 자체 적으로 제공하는 /login이 있음
        setFilterProcessesUrl("/api/members/login");
    }
    
   


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        try {
            // 요청에서 id와 password 추출
            MemberDTO loginData = new ObjectMapper().readValue(request.getInputStream(), MemberDTO.class);
            String id = loginData.getId(); // `username` 대신 `id` 사용
            String password = loginData.getPassword();
            
            UsernamePasswordAuthenticationToken authRequest =
                    new UsernamePasswordAuthenticationToken(id, password);

            return authenticationManager.authenticate(authRequest);

        } catch (IOException e) {
            throw new RuntimeException("로그인 요청 처리 중 오류 발생", e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) {
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        String username = customUserDetails.getUsername();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        String role = authorities.iterator().next().getAuthority();

        // Refresh Token 생성 (7일 유효기간)
        String refreshToken = jwtUtil.createRefreshToken(username, role, 60 * 60 * 24 * 7 * 1000L);

        // Redis에 Refresh Token 저장
        String redisKey = "refreshToken:" + username; // 사용자별 고유 키
        redisService.saveToken(redisKey, refreshToken, 60 * 60 * 24 * 7 * 1000L);

        // Refresh Token을 쿠키에 추가
        Cookie refreshTokenCookie = new Cookie("refreshToken", refreshToken);
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setSecure(true); // HTTPS만 허용
        refreshTokenCookie.setMaxAge(60 * 60 * 24 * 7); // 7일
        refreshTokenCookie.setPath("/");
        response.addCookie(refreshTokenCookie);

        System.out.println("Redis에 Refresh Token 저장 완료: " + refreshToken);

        // Access Token 생성 및 응답 헤더에 추가 (1시간 유효기간)
        String token = jwtUtil.createJwt(username, role, 60 *60 * 1000L);
        response.setStatus(200);
        response.addHeader("Authorization", "Bearer " + token);

        System.out.println("Access Token 발급 완료: " + token);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException failed) throws IOException {
        // 로그인 실패 시 추가 처리
    	 response.setStatus(401);
    }
}
