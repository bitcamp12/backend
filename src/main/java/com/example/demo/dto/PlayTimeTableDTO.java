package com.example.demo.dto;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PlayTimeTableDTO {
	private int playTimeTableSeq;
	private int playSeq;
	private int theaterSeq;
	private String startTime;
	private String endTime;
	private String startDisTime;
	private String endDisTime;
	private int minRate;
	private int maxRate;
	private LocalDate targetDate;
	private double discountRate;
	private double discountedPrice;
	private int price;
}
