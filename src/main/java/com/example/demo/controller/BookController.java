package com.example.demo.controller;

import java.util.List;
import java.util.Map;

import org.aspectj.apache.bcel.generic.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.aop.TimeTrace;
import com.example.demo.dto.BookDTO;
import com.example.demo.dto.TheaterDTO;
import com.example.demo.service.BookService;
import com.example.demo.util.ApiResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping(value = "/api/books")
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

	@PostMapping(value = "purchaseSeats")
	public ResponseEntity<ApiResponse<Void>> purchaseSeats(@RequestBody Map<String, Object> request) {
		System.out.println("Received Payload: " + request);

		List<Map<String, Object>> seatsMap = (List<Map<String, Object>>) request.get("seats");
		ObjectMapper objectMapper = new ObjectMapper();
		List<BookDTO> seats = objectMapper.convertValue(seatsMap, new TypeReference<List<BookDTO>>() {});
	
		// Process the user data
		Map<String, Object> user = (Map<String, Object>) request.get("user");
		System.out.println("User Data: " + user);

		int memberSeq = (Integer) user.get("memberSeq");
  		for (BookDTO seat : seats) {
        seat.setMemberSeq(memberSeq); // Set the memberSeq field
    }

		try {
			bookService.purchaseSeats(seats);
			return ResponseEntity.ok(new ApiResponse<>(200, "성공", null));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.ok(new ApiResponse<>(500, "서버 오류", null));
		}
	}
}
