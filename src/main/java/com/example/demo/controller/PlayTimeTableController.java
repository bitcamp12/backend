package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.PlayDiscountDTO;
import com.example.demo.dto.PlayTimeTableDTO;
import com.example.demo.service.PlayTimeTableService;
import com.example.demo.util.ApiResponse;

@RestController
@RequestMapping(value="/api/playTimeTables")
public class PlayTimeTableController {
    
	@Autowired
	private PlayTimeTableService playTimeTableService;
	
	@GetMapping("playTimeTables")
	public ResponseEntity<ApiResponse<List<PlayTimeTableDTO>>> playTimeTables(
			
			@RequestParam("playSeq") int playSeq,
			@RequestParam("targetDate") String targetDate) {
		try {

		List<PlayTimeTableDTO> list =playTimeTableService.playTimeTables(playSeq,targetDate);
		
		 // 리스트가 비어있지 않으면 성공 반환
        if (!list.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>(200, "성공", list));
        } else {
            // 데이터가 없으면 "데이터 없음" 반환
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>(404, "데이터 없음", list));
        }
	 } catch (Exception e) {
	        e.printStackTrace();  // 에러 출력
	        // 예외 발생 시 오류 처리
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                .body(new ApiResponse<>(500, "서버 오류", null));
	    }
	}

	@GetMapping("calculateDiscount")
    public ResponseEntity<ApiResponse<List<PlayDiscountDTO>>> calculateDiscount() {
        try {
            List<PlayDiscountDTO> discountedPlay = playTimeTableService.calculateDiscount();
            if (discountedPlay != null && !discountedPlay.isEmpty()) {
                return ResponseEntity.status(HttpStatus.OK)
                        .body(new ApiResponse<>(200, "성공적으로 데이터를 불러왔습니다", discountedPlay));
            } else {
                return ResponseEntity.status(HttpStatus.OK)
                        .body(new ApiResponse<>(404, "데이터 없음", discountedPlay));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(500, "서버에러가 발생했습니다", null));
        }
    }

	
}
