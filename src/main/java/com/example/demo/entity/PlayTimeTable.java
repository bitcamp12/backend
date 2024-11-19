package com.example.demo.entity;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PlayTimeTable {

	private int playTimeTableSeq;
	private int playSeq;
	private int theaterSeq;
	private LocalDateTime startTime;
	private LocalDateTime endTime;
	private LocalDateTime minRate;
	private LocalDateTime maxRate;
	private LocalDateTime targetDate;
}
