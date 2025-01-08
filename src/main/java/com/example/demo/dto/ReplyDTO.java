package com.example.demo.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReplyDTO {
    private int replySeq;
    private int qnaSeq;       // QnA 식별자만 전달
    private int memberSeq;    // Member 식별자만 전달
    private String content;
    private LocalDateTime createdDate;

}
