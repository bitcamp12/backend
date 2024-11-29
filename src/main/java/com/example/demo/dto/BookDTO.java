package com.example.demo.dto;

import java.time.LocalDateTime;

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
}
