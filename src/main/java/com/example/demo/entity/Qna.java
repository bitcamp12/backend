package com.example.demo.entity;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Qna {

	private int qnaSeq;
	private int playSeq;
	private int memberSeq;
	private String title;
	private String content;
	private LocalDateTime createdDate;
	
}
