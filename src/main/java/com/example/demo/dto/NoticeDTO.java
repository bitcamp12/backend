package com.example.demo.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class NoticeDTO {

	private int noticeSeq;
	private String title;
	private String content;
	private LocalDateTime createdDate;
	private LocalDateTime updatedDate;
	private String hide; // 'Y' or 'N'
	private String imageFileName;
	private String imageOriginalFileName;
}
