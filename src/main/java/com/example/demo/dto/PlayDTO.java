package com.example.demo.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PlayDTO implements Serializable{
	private int playSeq;
	private int memberSeq;         // 공연 관계자 pk
	private String name;
	private LocalDateTime startTime;
	private LocalDateTime endTime;
	private String imageFileName;
	private String imageOriginalFileName;
	private String description;
	private String address;
	private String totalActor;
	private int price;
	private String ageLimit;
    private int runningTime;
    private int discountedPrice;
    private int discountRate;
    private String targetDate; 

}
