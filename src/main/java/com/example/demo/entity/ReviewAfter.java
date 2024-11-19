package com.example.demo.entity;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReviewAfter {

	private int reviewAfterSeq;
	private int memberSeq;
	private int playSeq;
	private String content;
	private int rating;
	private LocalDateTime createdDate; // LocalDateTime 타입으로 변경
}
