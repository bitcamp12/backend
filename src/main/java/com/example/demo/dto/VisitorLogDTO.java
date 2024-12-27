package com.example.demo.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VisitorLogDTO {
	private Long visitorLogSeq;		 // 방문 기록 고유 번호
	private LocalDateTime visitTime; // 방문 시간
	private String dayOfWeek;		 // 요일 (Mon, Tue, ...)
	private int hourOfDay;	         // 시간대(0~23)
}
