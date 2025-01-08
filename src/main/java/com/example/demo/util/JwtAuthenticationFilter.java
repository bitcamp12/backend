package com.example.demo.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.demo.entity.Member;
import com.example.demo.service.CustomUserDetailsService;
import com.example.demo.service.RedisService;

import io.jsonwebtoken.ExpiredJwtException;
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
    
    
    @Autowired
    private AuthenticationFacade authenticationFacade;
    
    @Autowired
    private RedisService redisService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // 요청에서 액세스 토큰과 리프레시 토큰 추출
        String token = extractToken(request);


        // 토큰이 유효하면 인증 처리
        if (token != null) {
            processTokenAuthentication(token, request, response, filterChain);
        }

        // 필터 체인에 요청 전달
        filterChain.doFilter(request, response);
    }


    // 토큰 처리 메서드
    private void processTokenAuthentication(String token, HttpServletRequest request, HttpServletResponse response,FilterChain filterChain) throws IOException, ServletException {
    	 
        // 요청에서 액세스 토큰과 리프레시 토큰 추출
        String cookie = extractRefreshToken(request);
        
        String refreshKey = "refreshToken:" + jwtUtil.getUsername(cookie);
        String storedRefreshToken = redisService.getToken(refreshKey);
        
        // 1. Refresh Token 일치 여부 확인
        if (storedRefreshToken == null || !storedRefreshToken.equals(cookie)) {
            System.out.println("중복 로그인 감지 또는 잘못된 Refresh Token");
            
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            // 인증 거부 후 로그아웃 처리
            return; // 필터 체인 중단
        }
        

        // 리프레시 토큰을 사용하여 블랙리스트 확인 (여기서는 액세스 토큰을 블랙리스트에서 확인)
        String redisKeyBlack = "accessToken:" + jwtUtil.getUsername(cookie); // 사용자별 액세스 토큰 키
        String blacklistedToken = redisService.getToken(redisKeyBlack);

        // 2. 토큰이 블랙리스트에 존재하는 경우 인증 거부
        if (blacklistedToken != null && blacklistedToken.equals(token)) {
            System.out.println("블랙리스트에 포함된 토큰으로 인증 거부");
            
            // 인증 거부 후 401 Unauthorized 상태 코드 반환
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return; // 인증 거부 후 더 이상 필터 체인으로 진행하지 않음
        }
    	
    	
        	// System.out.println("토큰 만료 검증시작전: " ); // 만료 여부 출력
            boolean isExpired = jwtUtil.isExpired(token);
          //   System.out.println("토큰 만료 여부: " + isExpired); // 만료 여부 출력

            if (!isExpired) {
                // 토큰이 만료되지 않은 경우 인증 처리
                authenticateWithToken(token);
            } else {
                // 만료된 토큰인 경우 만료된 토큰 처리
                handleExpiredToken(request, response, filterChain);
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
        
        Member member = authenticationFacade.getCurrentMember();
        
        //System.out.println("이름"+member.getName()+"역할"+member.getRole());        
       // System.out.println("권한"+userDetails.getAuthorities());

      //  System.out.println("유효한 토큰, 인증 완료.");
    }

    // 만료된 토큰을 처리하고, 리프레시 토큰이 유효하면 새 액세스 토큰을 발급
    private void handleExpiredToken(HttpServletRequest request, HttpServletResponse response,FilterChain filterChain) throws IOException, ServletException {
        String refreshToken = extractRefreshToken(request);
       // String token = extractToken(request);
        
       // String usernameA = jwtUtil.getUsername(token);
        String usernameR = jwtUtil.getUsername(refreshToken);
        
//        if(usernameA != usernameR) {
//        	System.out.println("액세스토큰과 리프레쉬토큰 정보가 다릅니다");
//        	 return;
//        }
//        else {
//        	System.out.println(usernameA + "**"+usernameR +"**");
//        }
        
        String redisKey = "refreshToken:" + usernameR;
        
        String existingToken = redisService.getToken(redisKey);
        
        
        //redis에 가서 받은 리프레쉬 토큰이 있는지 검증 
        if (refreshToken != null && !jwtUtil.isExpired(refreshToken) && existingToken != null) {
          //  System.out.println("유효한 리프레시 토큰 존재");        		
           // 사용자별 리프레시 토큰을 저장하는 키
            // Redis에서 해당 키가 존재하는지 확인
            

            // 리프레시 토큰 삭제
            System.out.println("액세스 만료 리프레시 유효 액세스 재발급 진행 " + redisKey);   
          //  System.out.println("레디스에 존재하는 리프레쉬토큰"+refreshToken);

            String username = jwtUtil.getUsername(refreshToken);
            String role = jwtUtil.getRole(refreshToken);
            String newAccessToken = jwtUtil.createJwt(username, role, 60 * 1000 * 10L); // 10분 유효
//           System.out.println("새로운 액세스 토큰 생성: " + newAccessToken);

            response.setStatus(200);
            response.addHeader("Authorization", "Bearer " + newAccessToken);
            
            //새로 발급된 토큰으로 인증 자동 진행
            filterChain.doFilter(request, response);
        } else {
          //  System.out.println("리프레시 토큰이 없거나 만료됨.");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }  
        
    }

    // Authorization 헤더에서 JWT 토큰을 추출하는 메서드
    public String extractToken(HttpServletRequest request) {
        try {
            String header = request.getHeader("Authorization");
            System.out.println("헤더 값 추출 중JWT: " + header);

            if (header != null && header.startsWith("Bearer ")) {
                return header.substring(7); // "Bearer " 이후의 토큰 반환
            }
        } catch (Exception e) {
            // 예외 발생 시 로그를 출력하고 null 반환
            System.err.println("Jwt 추출 예외" + e.getMessage());
        }
        return null; // null 반환
    }


    // 리프레시 토큰을 쿠키에서 추출하는 메서드
    public String extractRefreshToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        //System.out.println("리프레쉬 쿠키 값 추출 중JWT: " + cookies);
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("refreshToken".equals(cookie.getName())) {
                   // System.out.println("리프레시 토큰 찾음: " + cookie.getValue());
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}
