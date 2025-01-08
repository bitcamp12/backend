package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class VisitorLogDTO {
	private Long visitorLogSeq;		 // 방문 기록 고유 번호
	private String visitTime; // 방문 시간
	private String dayOfWeek;		 // 요일 (Mon, Tue, ...)
	private Integer hourOfDay;	         // 시간대(0~23)
	
	//visitTime만
	public VisitorLogDTO() {}
	
	public VisitorLogDTO(String visitTime) {
		this.visitTime = visitTime;
	}
	
	//전체 필드 포함하는 생성자
	public VisitorLogDTO(Long visitorLogSeq, String visitTime, String dayOfWeek, Integer hourOfDay) {
		this.visitorLogSeq = visitorLogSeq;
		this.visitTime = visitTime;
		this.dayOfWeek = dayOfWeek;
		this.hourOfDay = hourOfDay;
	}
}
