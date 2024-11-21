package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.ReviewAfterDTO;
import com.example.demo.dto.ReviewBeforeDTO;
import com.example.demo.entity.ReviewAfter;
import com.example.demo.entity.ReviewBefore;
import com.example.demo.service.ReviewBeforeService;
import com.example.demo.util.ApiResponse;


@RestController
@RequestMapping(value="/api/reviewBefores")
public class ReviewBeforeController {
    
	@Autowired
	private ReviewBeforeService reviewBeforeService;
	
	//리뷰 작성
		@PostMapping("reviewBWrite")
		public ResponseEntity<ApiResponse<ReviewBefore>> reviewBWrite(@RequestParam("playSeq") int playSeq,
								@RequestBody ReviewBeforeDTO reviewBeforeDTO) {
			System.out.println(reviewBeforeDTO.getContent());
			try {
				int memberSeq=1;
//				memberService.getMemberSeq();
		//세션 구할거임
		 int result=reviewBeforeService.reviewBWrite(playSeq,memberSeq,reviewBeforeDTO.getContent());
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
		@GetMapping("getReviewBList")
		public  ResponseEntity<ApiResponse<List<ReviewBeforeDTO>>> getReviewBList(@RequestParam("playSeq") int playSeq) {
			
			
			try {
				List<ReviewBeforeDTO> list=reviewBeforeService.getReviewBList(playSeq);
				
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
			@GetMapping("getReviewB")
			public ResponseEntity<ApiResponse<ReviewBeforeDTO>> getReviewB(@RequestBody ReviewBeforeDTO reviewDTO) {
				try {
					System.out.println(reviewDTO.getReviewBeforeSeq());
					ReviewBeforeDTO reviewBeforeDTO= reviewBeforeService.getReviewB(reviewDTO.getReviewBeforeSeq());
					
					if(reviewBeforeDTO!=null) {
						return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(200, "성공", reviewBeforeDTO));
					}
					else {
						return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(404 , "데이터 없음", reviewBeforeDTO));
					}
					
				} catch (Exception e) {
					e.printStackTrace();
					return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(400, "오류", null));
				}
				
			}
		
		
		//리뷰 수정
			@PostMapping("reviewBUpdate")
			public ResponseEntity<ApiResponse<ReviewBefore>> reviewBUpdate(
			        @RequestBody ReviewBeforeDTO reviewBeforeDTO) {
			    try {
			        // 서비스 호출하여 업데이트
			        int result = reviewBeforeService.reviewBUpdate(reviewBeforeDTO.getReviewBeforeSeq(), reviewBeforeDTO.getContent());
			        
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
		@PostMapping("reviewBDelete")
		public ResponseEntity<ApiResponse<ReviewAfter>> reviewBDelete(@RequestParam("ReviewBeforeSeq") int  ReviewBeforeSeq) {
			
			
			try {
				int result=reviewBeforeService.reviewBDelete(ReviewBeforeSeq);
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
