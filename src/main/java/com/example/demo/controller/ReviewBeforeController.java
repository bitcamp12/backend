package com.example.demo.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.ReviewAfterDTO;
import com.example.demo.dto.ReviewBeforeDTO;
import com.example.demo.entity.ReviewAfter;
import com.example.demo.entity.ReviewBefore;
import com.example.demo.service.MemberService;
import com.example.demo.service.ReviewBeforeService;
import com.example.demo.util.ApiResponse;


@RestController
@RequestMapping(value="/api/reviewBefores")
public class ReviewBeforeController {
    
	@Autowired
	private ReviewBeforeService reviewBeforeService;
	@Autowired
	MemberService memberService;
	//리뷰 작성
		@PostMapping("reviewB")
		public ResponseEntity<ApiResponse<ReviewBefore>> reviewBWrite(@RequestParam("playSeq") int playSeq,
								@RequestBody ReviewBeforeDTO reviewBeforeDTO,
								@RequestParam("userId") String userId) {
			System.out.println(reviewBeforeDTO.getContent());
			try {
				reviewBeforeDTO.setMemberSeq(memberService.getMemberSeq(userId));
//				memberService.getMemberSeq();
		//세션 구할거임
		 int result=reviewBeforeService.reviewBWrite(playSeq,reviewBeforeDTO.getMemberSeq(),reviewBeforeDTO.getContent());
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
		@GetMapping("reviewBList")
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
			@GetMapping("reviewB")
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
			@PutMapping("reviewB")
			public ResponseEntity<ApiResponse<ReviewBefore>> reviewBUpdate(
			        @RequestBody ReviewBeforeDTO reviewBeforeDTO) {
				
				System.out.println(reviewBeforeDTO.getContent()+reviewBeforeDTO.getReviewBeforeSeq());
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
		@DeleteMapping("reviewB")
		public ResponseEntity<ApiResponse<ReviewAfter>> reviewBDelete(@RequestBody ReviewBeforeDTO reviewBeforeDTO) {
			
			
			try {
				int result=reviewBeforeService.reviewBDelete(reviewBeforeDTO.getReviewBeforeSeq());
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
		
		//갯수
		@GetMapping("ReviewBcount")
		public   ResponseEntity<ApiResponse<Integer>> ReviewBcount(@RequestParam("playSeq") int playSeq) {
			   try {
			        // 서비스 호출하여 업데이트
			        int count = reviewBeforeService.ReviewBcount(playSeq);
			        
			        if (count >= 0) {
			            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(200, "성공", count));
			        } else {
			            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(404 , "실패", null));
			        }

			    } catch (Exception e) {
			        e.printStackTrace();
			        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(400, "오류", null));
			    }
		}
	
		@GetMapping("ReviewBSearch")
		public  ResponseEntity<ApiResponse<List<ReviewBeforeDTO>>> ReviewBSearch(
				 @RequestParam("playSeq") int playSeq, 
			        @RequestParam("searchType") String searchType,
			        @RequestParam("keyword") String keyword
			        ) {
			 try {
			
	        List<ReviewBeforeDTO> list;
	        
			if(searchType.equals("id")) {
	        	list =reviewBeforeService.ReviewBSearchId(keyword,playSeq);
	        }
	        else if( searchType.equals("title")){
	        	
	        	list =reviewBeforeService.ReviewBSearchKey(keyword,playSeq);
	        	
	        }
	        else {
	        	  return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                          .body(new ApiResponse<>(400, "유효하지 않은 검색 타입", null));
	        }
	        

	        if (!list.isEmpty()) {
	            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(200, "성공", list));
	        } else {
	            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(404 , "리뷰 없음",  list));
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(400, "오류", null));
	    }
		
		
		}
}
