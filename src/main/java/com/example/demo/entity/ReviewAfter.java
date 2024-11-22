package com.example.demo.entity;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/*
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "review_after")
public class ReviewAfter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_after_seq")
    private int reviewAfterSeq;

    @ManyToOne
    @JoinColumn(name = "member_seq", nullable = false)
    private Member member;

    @ManyToOne
    @JoinColumn(name = "play_seq", nullable = false)
    private Play play;

    @Column(name = "content", nullable = false, length = 500)
    private String content;

    @Column(name = "rating")
    private int rating;

    @Column(name = "created_date", nullable = false, updatable = false)
    private LocalDateTime createdDate;
}

*/