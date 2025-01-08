package com.example.demo.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.example.demo.service.CustomUserDetails;
import com.example.demo.entity.Member;

@Component
public class AuthenticationFacade {

    /**
     * 현재 인증된 Authentication 객체를 가져옵니다.
     */
    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    /**
     * 현재 인증된 사용자 정보를 가져옵니다.
     * @return CustomUserDetails가 포함된 Member 객체 (로그인된 사용자), 또는 null
     */
    public Member getCurrentMember() {
        Authentication authentication = getAuthentication();
        if (authentication != null) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof CustomUserDetails) {
                return ((CustomUserDetails) principal).getMember();
            }
        }
        return null; // 인증되지 않은 경우
    }

    /**
     * 현재 인증된 사용자의 ID를 가져옵니다.
     * @return 사용자 ID (로그인된 사용자), 또는 null
     */
    public String getCurrentUserId() {
        Authentication authentication = getAuthentication();
        if (authentication != null) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof CustomUserDetails) {
                return ((CustomUserDetails) principal).getUsername();
            }
        }
        return null; // 인증되지 않은 경우
    }
}
