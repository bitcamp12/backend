package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "admin")
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "admin_seq")
    private int adminSeq;  // 관리자 번호 (Primary Key)

    @Column(name = "id", nullable = false, unique = true, length = 30)
    private String id;  // 아이디

    @Column(name = "name", nullable = false, length = 100)
    private String name;  // 이름

    @Column(name = "password", nullable = false, length = 200)
    private String password;  // 비밀번호
}
