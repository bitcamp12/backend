package com.example.demo.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PlayDTO {

	private int playSeq;
	private String name;
	private LocalDateTime startTime;
	private LocalDateTime endTime;
	private String imageFileName;
	private String imageOriginalFileName;
	private String description;
	private String address;
	private String totalActor;
	
}
