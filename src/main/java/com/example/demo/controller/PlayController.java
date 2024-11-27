package com.example.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.PlayDTO;
import com.example.demo.entity.Play;
import com.example.demo.service.PlayService;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.util.ApiResponse;


@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping(value="/api/plays")
public class PlayController {
    
	@Autowired
	private PlayService playService;
	
	
	@GetMapping("/getPlayOne")
	public ResponseEntity<ApiResponse<PlayDTO>> getPlayOne(@RequestParam("playSeq") String playSeq) {
	    System.out.println("Received playSeq: " + playSeq);

	    try {
	        PlayDTO playDTO = playService.getPlayOne(playSeq);
	        if (playDTO != null) {
	            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(200, "데이터 있음", playDTO));
	        } else {
	            return ResponseEntity.status(HttpStatus.OK)  // 잘못된 요청
	                    .body(new ApiResponse<>(400, "잘못된 요청", null));
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)  // 서버 에러
	                .body(new ApiResponse<>(500, "서버 에러", null));
	    }
	}

	//민웅 사용자 메인 페이지
	@GetMapping("/getPlayAll")
	public ResponseEntity<ApiResponse<List<PlayDTO>>> getPlayAll(@RequestParam("page") int page, @RequestParam("size") int size) {
		List<PlayDTO> plays = playService.getPlayAll(page, size);
		return ResponseEntity.ok(new ApiResponse<>(200, "Data retrieved", plays));
	}	
}
