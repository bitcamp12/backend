package com.example.demo.config;
/*
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {

	@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
            // 로그인 및 로그아웃 설정
            .formLogin(formLogin ->
                formLogin
                    .loginPage("/secure/login")  // 사용자 정의 로그인 페이지 URL
                    .permitAll()  // 로그인 페이지는 모든 사용자에게 허용
            )
            .logout(logout ->
                logout
                    .permitAll()  // 로그아웃은 모든 사용자에게 허용
            )
            // HTTP 요청에 대한 권한 설정
            .authorizeRequests(authorizeRequests ->
                authorizeRequests
                    .requestMatchers("/api/**").permitAll()  // /api/** 경로는 인증 없이 접근 가능
                    .anyRequest().authenticated()  // 그 외 모든 요청은 인증된 사용자만 접근 가능
            )
            // CSRF 보호 비활성화 (API 서버나 특정 필요에 따라)
            .csrf(csrf -> csrf.disable());

        return http.build();
    }
}
*/