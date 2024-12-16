package com.example.demo.dto;

import java.time.LocalDateTime;

import com.example.demo.entity.Book.PaymentStatus;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BookDTO {

    private int bookSeq;
    private int memberSeq;
    private int playTimeTableSeq;
    private LocalDateTime payDate;
    private int bookedX;
    private int bookedY;
    private String payment;
    private int totalPrice;
    private PaymentStatus paymentStatus = PaymentStatus.PAID; // 기본값 설정
    
}
