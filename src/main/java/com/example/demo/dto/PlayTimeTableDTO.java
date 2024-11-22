package com.example.demo.dto;

import java.time.LocalDate;

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
}
