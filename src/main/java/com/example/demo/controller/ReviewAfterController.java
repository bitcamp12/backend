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
import com.example.demo.entity.Member;
import com.example.demo.entity.ReviewAfter;
import com.example.demo.service.MemberService;
import com.example.demo.service.ReviewAfterService;
import com.example.demo.util.ApiResponse;
import com.example.demo.util.AuthenticationFacade;

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
	
	@Autowired
	AuthenticationFacade authenticationFacade;
	
	//리뷰 작성
	@PostMapping("ReviewA")
	public ResponseEntity<ApiResponse<ReviewAfter>> reviewWriteA(@RequestParam("playSeq") int playSeq,
							@RequestBody ReviewAfterDTO reviewDTO,
							HttpSession session) {
		 Member member = authenticationFacade.getCurrentMember();
		 System.out.println("현재로그인아이디"+member.getId());  // 아이디 가져오는예시 
		
		try {
			reviewDTO.setMemberSeq(memberService.getMemberSeq(member.getId()));
//			
	//세션 구할거임
	 int result=reviewAfterService.reviewAWrite(playSeq,reviewDTO.getMemberSeq(),reviewDTO.getContent(),reviewDTO.getRating());
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
	public  ResponseEntity<ApiResponse<List<ReviewAfterDTO>>> getReviewAList(
			@RequestParam("playSeq") int playSeq,
	        @RequestParam("selected") String selected,
	        @RequestParam(defaultValue = "1",name = "page") int page, 
	        @RequestParam("size") int size) {
		System.out.println(selected+size+page);
		//DTO 에 id 추가
		try {
	        List<ReviewAfterDTO> list;
	        
	        // 문자열 비교 시 equals() 사용
	        if (selected.equals("latest")) {
	            list = reviewAfterService.getReviewAList(playSeq,page,size);  // 최신순
	        } else if (selected.equals("rating")) {
	            list = reviewAfterService.getReviewAListStar(playSeq,page,size);  // 별점순
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
	
	//
	
	//리뷰 출력
		@GetMapping("ReviewA")
		public ResponseEntity<ApiResponse<ReviewAfterDTO>> getReviewA(@RequestBody ReviewAfterDTO reviewDTO) {
			
			//
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
			   Float average = reviewAfterService.ReviewAAvg(playSeq);
			   float avg = (average != null) ? average : 0.0f;
		        System.out.println(avg+"avg");
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
	public ResponseEntity<ApiResponse<List<ReviewAfterDTO>>> ReviewASearch(
			 @RequestParam("playSeq") int playSeq, 
		        @RequestParam("searchType") String searchType,
		        @RequestParam("keyword") String keyword,
		        @RequestParam("selected") String selected,
		        @RequestParam(defaultValue = "1",name = "page") int page, 
		        @RequestParam("size") int size) {
		try {
	        

	        List<ReviewAfterDTO> list;
	        if(searchType.equals("id")) {
	        	
	        	if(selected.equals("latest")) {
	        		///날짜순
	        		System.out.println(1);
	        		list = reviewAfterService.ReviewASearchIdDate(keyword,playSeq,page,size);
	        	}
	        	else {
	        		//별점순
	        		System.out.println(2);
	        		list = reviewAfterService.ReviewASearchIdRating(keyword,playSeq,page,size);
	        	}
	        	
	        }
	        else if( searchType.equals("title")){
	        	
	        	if(selected.equals("latest")) {
	        		///날짜순
	        		System.out.println(3);
	        		list = reviewAfterService.ReviewASearchDate(keyword,playSeq,page,size);
	        	}
	        	else {
	        		//별점순
	        		System.out.println(4);
	        		list = reviewAfterService.ReviewASearchRating(keyword,playSeq,page,size);
	        	}
	        	
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
	
	@GetMapping("ReviewASearchCount")
	public ResponseEntity<ApiResponse<Integer>> ReviewASearchCount(
			 @RequestParam("playSeq") int playSeq, 
		        @RequestParam("searchType") String searchType,
		        @RequestParam("keyword") String keyword,
		        @RequestParam("selected") String selected
		        ) {
		try {
	        

	       int count;
	        if(searchType.equals("id")) {
	        	
	        	if(selected.equals("latest")) {
	        		///날짜순
	        		System.out.println(1);
	        		count = reviewAfterService.ReviewASearchIdDateCount(keyword,playSeq);
	        	}
	        	else {
	        		//별점순
	        		System.out.println(2);
	        		count = reviewAfterService.ReviewASearchIdRatingCount(keyword,playSeq);
	        	}
	        	
	        }
	        else if( searchType.equals("title")){
	        	
	        	if(selected.equals("latest")) {
	        		///날짜순
	        		System.out.println(3);
	        		count = reviewAfterService.ReviewASearchDateCount(keyword,playSeq);
	        	}
	        	else {
	        		//별점순
	        		System.out.println(4);
	        		count = reviewAfterService.ReviewASearchRatingCount(keyword,playSeq);
	        	}
	        	
	        }
	        else {
	        	  return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                          .body(new ApiResponse<>(400, "유효하지 않은 검색 타입", null));
	        }
	        
	        if (count!=0) {
	            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(200, "성공", count));
	        } else {
	            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(404 , "리뷰 없음",  0));
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(400, "오류", null));
	    }
		
		
	}
	

}
