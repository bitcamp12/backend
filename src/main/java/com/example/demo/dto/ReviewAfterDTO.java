package com.example.demo.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReviewAfterDTO {

	private int reviewAfterSeq;
	private int memberSeq;
	private int playSeq;
	private String content;
	private int rating;
	private LocalDateTime createdDate; // LocalDateTime 타입으로 변경
	private String id; // id 리액트에서 필요해서
}
