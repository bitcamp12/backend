package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.NoticeDTO;
import com.example.demo.service.NoticeService;
import com.example.demo.util.ApiResponse;


@RestController
@RequestMapping(value="/api/notices")
@CrossOrigin(origins = "http://localhost:3000")
public class NoticeController {
    
	@Autowired
	private NoticeService noticeService;

	//민웅 공지사항 전체 조회
	@GetMapping("/getNoticeAll")
	public ResponseEntity<ApiResponse<List<NoticeDTO>>> getNoticeAll() {
		List<NoticeDTO> notices = noticeService.getNoticeAll();
		return ResponseEntity.ok(new ApiResponse<>(200, "Data retrieved", notices));
	}
}
