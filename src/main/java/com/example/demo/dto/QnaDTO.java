package com.example.demo.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class QnaDTO {

	private int qnaSeq;
	private int playSeq;
	private int memberSeq;
	private String title;
	private String content;
	private LocalDateTime createdDate;
	private String id;
}
