package com.example.demo.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
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
	@PostMapping("ReviewA")
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
		 return   ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(500 , "실패", null));
	 } 
			
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(400, "오류", null));
		}
		
		
		
	}
	
	//리뷰 list 출력
	@GetMapping("ReviewAList")
	public  ResponseEntity<ApiResponse<List<ReviewAfterDTO>>> getReviewAList(@RequestParam("playSeq") int playSeq,
			@RequestParam("selected") String selected) {
		System.out.println(selected);
		
		try {
	        List<ReviewAfterDTO> list;
	        
	        // 문자열 비교 시 equals() 사용
	        if (selected.equals("latest")) {
	            list = reviewAfterService.getReviewAList(playSeq);  // 최신순
	        } else if (selected.equals("rating")) {
	            list = reviewAfterService.getReviewAListStar(playSeq);  // 별점순
	        } else {
	            // "latest"나 "rating" 외의 값이 들어왔을 경우 처리
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
	                    .body(new ApiResponse<>(400, "잘못된 요청", null));
	        }

	        System.out.println(list);
	        
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
	
	//리뷰 출력
		@GetMapping("ReviewA")
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
		@PutMapping("ReviewA")
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
	@DeleteMapping("ReviewA")
	public ResponseEntity<ApiResponse<ReviewAfter>> reviewADelete(@RequestBody ReviewAfterDTO reviewDTO) {
		
		
		try {
			int result=reviewAfterService.reviewADelete(reviewDTO.getReviewAfterSeq());
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
	@GetMapping("ReviewAcount")
	public   ResponseEntity<ApiResponse<Integer>> ReviewAcount(@RequestParam("playSeq") int playSeq) {
		   try {
		        // 서비스 호출하여 업데이트
		        int count = reviewAfterService.reviewACount(playSeq);
		        
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
	
	
	
	//별점 평균
	@GetMapping("ReviewAAvg")
	public   ResponseEntity<ApiResponse<Float>> ReviewAAvg(@RequestParam("playSeq") int playSeq) {
		   try {
		        // 서비스 호출하여 업데이트
			   Float avg = reviewAfterService.ReviewAAvg(playSeq);
		        
		        if (avg >= 0) {
		            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(200, "성공", avg));
		        } else {
		            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(404 , "실패",  0f));
		        }

		    } catch (Exception e) {
		        e.printStackTrace();
		        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(400, "오류", null));
		    }
	}
	
	
	
	@GetMapping("ReviewASearch")
	public ResponseEntity<ApiResponse<List<ReviewAfterDTO>>> ReviewASearch(@RequestBody Map<String, String> requestBody) {
		try {
	        // 서비스 호출하여 업데이트
			String searchType = requestBody.get("searchType");
	        String keyword = requestBody.get("keyword");
	        String selected=requestBody.get("selected");

	        List<ReviewAfterDTO> list;
	        if(searchType.equals("아이디")) {
	        	
	        	if(selected.equals("latest")) {
	        		///날짜순
	        		list = reviewAfterService.ReviewASearchId(keyword);
	        	}
	        	else {
	        		//별점순
	        		list = reviewAfterService.ReviewASearchId(keyword);
	        	}
	        	
	        }
	        else if( searchType.equals("내용")){
	        	
	        	if(selected.equals("latest")) {
	        		///날짜순
	        		list = reviewAfterService.ReviewASearch(keyword);
	        	}
	        	else {
	        		//별점순
	        		list = reviewAfterService.ReviewASearch(keyword);
	        	}
	        	
	        }
	        else {
	        	  return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                          .body(new ApiResponse<>(400, "유효하지 않은 검색 타입", null));
	        }
	        
	        if (!list.isEmpty()) {
	            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(200, "성공", list));
	        } else {
	            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(404 , "실패",  list));
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(400, "오류", null));
	    }
		
		
	}
	

}
