package com.example.demo.dto;


import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CheckMyBookDTO {
	private String id;
	
	private int bookSeq;
	private LocalDateTime payDate;
	private String payment;
	
	private String name;
	
	private LocalDateTime targetDate;
	 
}
