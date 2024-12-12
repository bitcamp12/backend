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
        System.out.println("검증 진행중: " + id); //id가 username으로 명시하엿음

        // DB에서 조회
        Member member = memberRepository.findById(id);

        if (member != null) {
            System.out.println("DB에서 가져온 ID: " + member.getId());
            
            
            
            return new CustomUserDetails(member); // UserDetails 구현체 반환
        }

        throw new UsernameNotFoundException("User not found with ID: " + id);
    }
}
