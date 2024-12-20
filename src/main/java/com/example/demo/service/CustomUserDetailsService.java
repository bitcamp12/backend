package com.example.demo.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Member;
import com.example.demo.repository.MemberRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    public CustomUserDetailsService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        // DB에서 회원 조회
        Member member = memberRepository.findById(id);

        // 셀러와 어드민 사용자의 로그인 방지
        if (member != null) {
            // Role enum 값을 직접 비교 (ROLE을 붙이지 않음)
            if (member.getRole() == Member.Role.SELLER) {
                throw new UsernameNotFoundException("허용되지않은 사용자입니다. " + member.getRole());
            }

            // UserDetails 객체 반환
            return new CustomUserDetails(member); // UserDetails 구현체 반환
        }

        // 사용자 정보가 없을 경우 예외 던짐
        throw new UsernameNotFoundException("User not found with ID: " + id);
    }


}
