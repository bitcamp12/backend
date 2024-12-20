package com.example.demo.util;

import java.util.Arrays;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import com.example.demo.service.CustomUserDetailsService;
import com.example.demo.service.RedisService;

import jakarta.servlet.http.HttpServletRequest;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final AuthenticationConfiguration authenticationConfiguration;

    @Autowired
    private JWTUtil jwtUtil;
    
    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    
    @Autowired
    private RedisService redisService;


    public SecurityConfig(AuthenticationConfiguration authenticationConfiguration) {
        this.authenticationConfiguration = authenticationConfiguration;
    }
    
    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {
        return new JwtAuthenticationFilter(jwtUtil, authenticationManager(),customUserDetailsService);
    }
    

    // AuthenticationManager Bean 등록
    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    // BCryptPasswordEncoder Bean 등록
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Security Filter Chain 설정
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    		
    		http
                .cors((corsCustomizer -> corsCustomizer.configurationSource(new CorsConfigurationSource() {

                    @Override
                    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {

                        CorsConfiguration configuration = new CorsConfiguration();

			configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000", "http://211.188.57.64"));
                        configuration.setAllowedMethods(Collections.singletonList("*"));
                        configuration.setAllowCredentials(true);
                        configuration.setAllowedHeaders(Collections.singletonList("*"));
                        configuration.setMaxAge(3600L);

    										configuration.setExposedHeaders(Collections.singletonList("Authorization"));

                        return configuration;
                    }
                })));

			//csrf disable
            http
                    .csrf((auth) -> auth.disable());

    				//From 로그인 방식 disable
            http
                    .formLogin((auth) -> auth.disable());

    				//http basic 인증 방식 disable
            http
                    .httpBasic((auth) -> auth.disable());
            
				//세션 설정
            http
                    .sessionManagement((session) -> session
                            .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

    				//경로별 인가 작업
            http
            .authorizeHttpRequests((auth) -> auth
                    .requestMatchers(             		
                    		"/api/favorites/favorites",                  		
                    		"/api/qnas/qna",                   		
                    		"/api/reviewAfters/ReviewA",
                    		"/api/reviewBefores/ReviewB",                    		
                    		"/api/books/getBookedSeats",
                    		"/api/books/purchaseSeats",                   		
                    		"/api/theaters/getTheaterInfo",                  		
                            "/api/members/getUserInfo/me",
                            "/api/members/infoWithdrawal/me",
                            "/api/members/checkMyBook",
                            "/api/members/checkMyBook/checkBookingsByDate"
                        ).hasAuthority("ROLE_USER") // ROLE_USER 권한을 가진 사용자만 접근 가능
                .requestMatchers("/**").permitAll()) // 기본 페이지는 전체 허용    
            .addFilterAt(new LoginFilter(authenticationManager(), jwtUtil, redisService), UsernamePasswordAuthenticationFilter.class) // LoginFilter 추가
            .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class); // JWT 필터 추가

            return http.build();
        }
}
