package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dao.ReplyDAO;
import com.example.demo.dto.ReplyDTO;
import com.example.demo.entity.ReviewAfter;
import com.example.demo.util.ApiResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping(value="/api/replys")
public class ReplyController {
	@Autowired
	private ReplyDAO replyDAO;
	
	@GetMapping("Reply")
	public ResponseEntity<ApiResponse<ReplyDTO>> getReply(@RequestParam("qnaSeq") String qnaSeq) {
		try {
		ReplyDTO replyDTO=replyDAO.getReply(qnaSeq);
		System.out.println(replyDTO.getContent());
		if(replyDTO!=null) {
			return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(200, "성공", replyDTO));
		}
		else {
			return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(404 , "데이터 없음", replyDTO));
		}
		
	} catch (Exception e) {
		e.printStackTrace();
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(400, "오류", null));
	}
		
		
	}
	

}
