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
@Table(name = "qna")
public class Qna {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "qna_seq")
    private int qnaSeq;

    @ManyToOne
    @JoinColumn(name = "play_seq", nullable = false)
    private Play play;

    @ManyToOne
    @JoinColumn(name = "member_seq", nullable = false)
    private Member member;

    @Column(name = "title", length = 200)
    private String title;

    @Column(name = "content", length = 200)
    private String content;

    @Column(name = "created_date", nullable = false, updatable = false)
    private LocalDateTime createdDate;
}