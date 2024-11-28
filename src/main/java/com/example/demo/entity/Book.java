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
@Table(name = "book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_seq")
    private int bookSeq; 

    @ManyToOne
    @JoinColumn(name = "member_seq", nullable = false)
    private Member member;

    @ManyToOne
    @JoinColumn(name = "play_time_table_seq", nullable = false)
    private PlayTimeTable playTimeTable;

    @Column(name = "pay_date", nullable = false, updatable = false)
    private LocalDateTime payDate;

    @Column(name = "booked_x", nullable = false)
    private int bookedX;

    @Column(name = "booked_y", nullable = false)
    private int bookedY;

    @Column(name = "payment", length = 100)
    private String payment;
    
    @Column(name = "total_price")
    private int totalPrice;
}