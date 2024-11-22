package com.example.demo.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "member")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_seq")
    private int memberSeq;  // 회원 번호 (Primary Key)

    @Column(name = "id", nullable = false, unique = true, length = 30)
    private String id;  // 아이디

    @Column(name = "name", nullable = false, length = 100)
    private String name;  // 이름

    @Column(name = "password", nullable = false, length = 200)
    private String password;  // 비밀번호

    @Column(name = "email", nullable = false, length = 200)
    private String email;  // 이메일

    @Column(name = "phone", nullable = false, length = 50)
    private String phone;  // 전화번호

    @Column(name = "address", length = 500)
    private String address;  // 주소

    @Enumerated(EnumType.STRING)
    @Column(name = "gender", length = 1)
    private Gender gender;  // 성별 (ENUM)

    @Column(name = "sns_token", length = 200)
    private String snsToken;  // SNS 토큰

    @Enumerated(EnumType.STRING)
    @Column(name = "role", length = 10)
    private Role role;  // 역할 (ENUM)

    @Column(name = "register_date", nullable = false, columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime registerDate;  // 가입 날짜

    public enum Gender {
        M, F, O
    }

    public enum Role {
        USER, SELLER
    }
    
}