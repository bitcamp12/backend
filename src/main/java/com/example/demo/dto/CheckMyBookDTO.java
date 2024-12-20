package com.example.demo.dto;


import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
public class CheckMyBookDTO {
	@JsonProperty("memberId")
	private String memberId;
	
	@JsonProperty("bookSeq")
	private int bookSeq;
	@JsonProperty("payDate")
	private LocalDateTime payDate;
	@JsonProperty("payment")
	private String payment;
	@JsonProperty("paymentStatus")
	private String paymentStatus;
	
	@JsonProperty("playName")
	private String playName;
	
	@JsonProperty("targetDate")
	private LocalDateTime targetDate;
	 
}
