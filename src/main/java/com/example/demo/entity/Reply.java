package com.example.demo.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "reply")
public class Reply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reply_seq")
    private int replySeq;

    @ManyToOne
    @JoinColumn(name = "qna_seq", nullable = false)
    private Qna qna;

    @ManyToOne
    @JoinColumn(name = "member_seq", nullable = false)
    private Member member;

    @Column(name = "content", length = 1000)
    private String content;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

}