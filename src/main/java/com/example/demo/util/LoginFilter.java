package com.example.demo.util;

import com.example.demo.dto.member.MemberDTO;
import com.example.demo.service.CustomUserDetails;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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

    public LoginFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        
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
				
				//UserDetailsS
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();

        String username = customUserDetails.getUsername();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();

        String role = auth.getAuthority();


        
        
        // 리프레시 토큰 생성 (만료 시간 7일)
        String refreshToken = jwtUtil.createRefreshToken(username, role, 60 * 60 * 24 * 7 * 1000L); // 7일
           
        // 리프레시 토큰을 쿠키에 설정
        Cookie refreshTokenCookie = new Cookie("refreshToken", refreshToken);
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setSecure(true); // HTTPS에서만 전송
        refreshTokenCookie.setMaxAge(60 * 60 * 24 * 7); // 7일 유효
        refreshTokenCookie.setPath("/"); // 전체 도메인에서 접근 가능
        response.addCookie(refreshTokenCookie);
        
        System.out.println("리프레쉬발급"+refreshToken);
        //액세스 토큰
        
        String token = jwtUtil.createJwt(username, role, 60 * 60 * 10000L); //60 * 60 * 1000L 60분
        response.setStatus(200);
        response.addHeader("Authorization", "Bearer " + token);
        
        System.out.println("액세스토큰발급"+token);
        
  
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException failed) throws IOException {
        // 로그인 실패 시 추가 처리
    	 response.setStatus(401);
    }
}
