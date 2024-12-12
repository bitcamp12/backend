package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.TheaterDTO;
import com.example.demo.service.ReviewBeforeService;
import com.example.demo.service.TheaterService;
import com.example.demo.util.ApiResponse;


@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping(value="/api/theaters")
public class TheaterController {
    
	@Autowired
	private TheaterService reviewBeforeService;

	@GetMapping("getTheaterInfo")
	public ResponseEntity<ApiResponse<List<TheaterDTO>>> getTheaterInfo(
			) {
		try {
		List<TheaterDTO> list =reviewBeforeService.getTheaterInfo();
		
		 // 리스트가 비어있지 않으면 성공 반환
		if (!list.isEmpty()) {
			return ResponseEntity.ok(new ApiResponse<>(200, "성공", list));
		} else {
			// 데이터가 없으면 "데이터 없음" 반환
			return ResponseEntity.ok(new ApiResponse<>(404, "데이터 없음", list));
		}
	 } catch (Exception e) {
	        e.printStackTrace();  // 에러 출력
	        // 예외 발생 시 오류 처리
	        return ResponseEntity.ok(new ApiResponse<>(500, "서버 오류", null));
	    }
	}

	
}
