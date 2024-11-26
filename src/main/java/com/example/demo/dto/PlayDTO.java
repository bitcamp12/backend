package com.example.demo.dto;

import java.time.LocalDate;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PlayDTO {
	private int playSeq;
	private int memberSeq;         // 공연 관계자 pk
	private String name;
	private LocalDate startTime;
	private LocalDate endTime;
	private String imageFileName;
	private String imageOriginalFileName;
	private String description;
	private String address;
	private String totalActor;
}
