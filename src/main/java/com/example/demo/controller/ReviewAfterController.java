package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.PlayDTO;
import com.example.demo.dto.ReviewAfterDTO;
import com.example.demo.entity.ReviewAfter;
import com.example.demo.service.MemberService;
import com.example.demo.service.ReviewAfterService;
import com.example.demo.util.ApiResponse;

import jakarta.servlet.http.HttpSession;
import lombok.Delegate;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;




@RestController
@RequestMapping(value="/api/reviewAfters")
public class ReviewAfterController {
    
	@Autowired
	private ReviewAfterService reviewAfterService;
	
	@Autowired
	MemberService memberService;
	
	//리뷰 작성
	@PostMapping("reviewAWrite")
	public ResponseEntity<ApiResponse<ReviewAfter>> reviewWriteA(@RequestParam("playSeq") int playSeq,
							@RequestBody ReviewAfterDTO reviewDTO) {
		System.out.println(reviewDTO.getContent());
		try {
			int memberSeq=1;
//			memberService.getMemberSeq();
	//세션 구할거임
	 int result=reviewAfterService.reviewAWrite(playSeq,memberSeq,reviewDTO.getContent(),reviewDTO.getRating());
	 System.out.println(result);
	
	 if(result==1) {
		 return  ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(200, "성공", null));
	 }
	 else { 
		 return   ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(404 , "실패", null));
	 } 
			
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(400, "오류", null));
		}
		
		
		
	}
	
	//리뷰 list 출력
	@GetMapping("getReviewAList")
	public  ResponseEntity<ApiResponse<List<ReviewAfterDTO>>> getReviewAList(@RequestParam("playSeq") int playSeq) {
		
		
		try {
			List<ReviewAfterDTO> list=reviewAfterService.getReviewAList(playSeq);
			
			System.out.println(list);
			if(!list.isEmpty()) {
				
				return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(200, "성공", list));
			}
			else {
				return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(404 , "데이터 없음", list));
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(400, "오류", null));
		}

	}
	
	//리뷰 출력
		@GetMapping("getReviewA")
		public ResponseEntity<ApiResponse<ReviewAfterDTO>> getReviewA(@RequestBody ReviewAfterDTO reviewDTO) {
			try {
				System.out.println(reviewDTO.getReviewAfterSeq());
				ReviewAfterDTO reviewAfterDTO= reviewAfterService.getReviewA(reviewDTO.getReviewAfterSeq());
				
				if(reviewAfterDTO!=null) {
					return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(200, "성공", reviewAfterDTO));
				}
				else {
					return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(404 , "데이터 없음", reviewAfterDTO));
				}
				
			} catch (Exception e) {
				e.printStackTrace();
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(400, "오류", null));
			}
			
		}
	
	
	//리뷰 수정
		@PostMapping("reviewAUpdate")
		public ResponseEntity<ApiResponse<ReviewAfter>> reviewAUpdate(
		        @RequestBody ReviewAfterDTO reviewDTO) {
		    
		    try {
		        // 서비스 호출하여 업데이트
		        int result = reviewAfterService.reviewAUpdate(reviewDTO.getReviewAfterSeq(), reviewDTO.getContent(), reviewDTO.getRating());
		        
		        if (result == 1) {
		            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(200, "성공", null));
		        } else {
		            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(404 , "실패", null));
		        }

		    } catch (Exception e) {
		        e.printStackTrace();
		        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(400, "오류", null));
		    }
		}

	
	//리뷰 삭제
	@PostMapping("reviewADelete")
	public ResponseEntity<ApiResponse<ReviewAfter>> reviewADelete(@RequestParam("reviewAfterSeq") int  reviewAfterSeq) {
		
		
		try {
			int result=reviewAfterService.reviewADelete(reviewAfterSeq);
		if(result==1) {
			 return  ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(200, "성공", null));
		 }
		 else { 
			 return   ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(404 , "실패", null));
		 } 
	
			
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(400, "오류", null));
		}
		
	}
	
	
}
