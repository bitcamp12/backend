package com.example.demo.entity;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Member {

    private int memberSeq;         // 회원 번호 (Primary Key)
    private String id;             // 아이디
    private String name;           // 이름
    private String password;       // 비밀번호
    private String email;          // 이메일
    private String phone;          // 전화번호
    private String address;        // 주소
    private String gender;         // 성별 (ENUM)
    private String snsToken;       // SNS 토큰
    private String role;             // 역할 (ENUM)
    private LocalDateTime registerDate; // 가입 날짜
    
}
