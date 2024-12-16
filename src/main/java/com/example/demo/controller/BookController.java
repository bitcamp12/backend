package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.BookDTO;
import com.example.demo.dto.TheaterDTO;
import com.example.demo.service.BookService;
import com.example.demo.util.ApiResponse;

@RestController
@RequestMapping(value = "/api/books")
@CrossOrigin(origins = "http://localhost:3000")
public class BookController {

	@Autowired
	private BookService bookService;

	@GetMapping(value = "getBookedSeats")
	public ResponseEntity<ApiResponse<List<BookDTO>>> getBookedSeats(@RequestParam("playTimeTableSeq") int playTimeTableSeq) {
		try {
			List<BookDTO> list = bookService.getBookedSeats(playTimeTableSeq);

			if (!list.isEmpty()) {
				return ResponseEntity.ok(new ApiResponse<>(200, "성공", list));
			} else {
				return ResponseEntity.ok(new ApiResponse<>(404, "데이터 없음", list));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.ok(new ApiResponse<>(500, "서버 오류", null));
		}
	}
}
