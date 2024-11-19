package com.example.demo.entity;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReviewBefore {

	private int reviewBeforeSeq;
	private int memberSeq;
	private int playSeq;
	private String content;
	private LocalDateTime createdDate;
}
