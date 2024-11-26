package com.example.demo.dto;

import java.time.LocalDateTime;

import com.example.demo.entity.Member.Gender;
import com.example.demo.entity.Member.Role;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class MemberDTO {

	private int memberSeq;  // 회원 번호
    private String id;  // 아이디
    private String name;  // 이름
    private String email;  // 이메일
    private String phone;  // 전화번호
    private String address;  // 주소
    private String gender;  // 성별
    private String snsToken;  // SNS 토큰
    private String role;  // 역할
    private LocalDateTime registerDate;  // 가입 날짜
}
