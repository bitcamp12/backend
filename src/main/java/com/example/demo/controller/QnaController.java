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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.QnaDTO;
import com.example.demo.dto.ReviewBeforeDTO;
import com.example.demo.entity.Member;
import com.example.demo.entity.Qna;
import com.example.demo.service.MemberService;
import com.example.demo.service.QnaService;
import com.example.demo.util.ApiResponse;
import com.example.demo.util.AuthenticationFacade;

import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping(value="/api/qnas")
public class QnaController {
    
	@Autowired
	private QnaService qnaService;
	@Autowired
	MemberService memberService;
	@Autowired
	AuthenticationFacade authenticationFacade;
	
	@PostMapping("qna")
	public ResponseEntity<ApiResponse<Qna>> qnaWrite(@RequestParam("playSeq") int playSeq,
			@RequestBody QnaDTO qnaDTO,
			HttpSession session) {
		 Member member = authenticationFacade.getCurrentMember();
		 System.out.println("현재로그인아이디"+member.getId());  // 아이디 가져오는예시 
		
		
		  System.out.println("Received QnaDTO: " + qnaDTO); // 객체 전체 출력
		    System.out.println("Title: " + qnaDTO.getTitle());
		    System.out.println("Content: " + qnaDTO.getContent());
		
		try {
			qnaDTO.setMemberSeq(memberService.getMemberSeq(member.getId()));
			int result=qnaService.qnaWrite(playSeq,qnaDTO.getMemberSeq(),qnaDTO.getTitle(),qnaDTO.getContent());
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
	
	
	//qa list->공연 seq
	@GetMapping("qnaList")
	public ResponseEntity<ApiResponse<List<QnaDTO>>> getQnaList(
			@RequestParam("playSeq") int playSeq,
			@RequestParam(defaultValue = "1",name = "page") int page, 
	        @RequestParam("size") int size) {
		
		List<QnaDTO> list =qnaService.getQnaList(playSeq,page,size);
		try {
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
	
	
	
	//qa one 조회
	@GetMapping("qna")
	public ResponseEntity<ApiResponse<QnaDTO>> getQnaOne(@RequestBody QnaDTO qnaDTO) {
		QnaDTO DTO=qnaService.getQnaOne(qnaDTO.getQnaSeq());
		
		try {
			if(DTO!=null) {
				
				return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(200, "성공", DTO));
			}
			else {
				return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(404 , "데이터 없음", DTO));
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(400, "오류", null));
		}
	}
	
	
	
	
	
	//aq 수정
	
	@PutMapping("qna")
	public ResponseEntity<ApiResponse<Qna>> updateQna(@RequestBody QnaDTO qnaDTO) {
		
		try {
			System.out.println(qnaDTO.getQnaSeq()+"하요");
			int result=qnaService.updateQna(qnaDTO.getQnaSeq(),qnaDTO.getTitle(),qnaDTO.getContent());
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
	
	//qa 삭제
	@DeleteMapping("qna")
	public ResponseEntity<ApiResponse<Qna>> deleteQna(@RequestBody QnaDTO qnaDTO) {
		int result=qnaService.deleteQna(qnaDTO.getQnaSeq());
		
		try {
			if(result==1) {
				
				return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(200, "성공", null));
			}
			else {
				return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(404 , "데이터 없음", null));
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(400, "오류", null));
		}
		
		
	}
	

	//갯수
	@GetMapping("qnaCount")
	public   ResponseEntity<ApiResponse<Integer>> qnaCount(@RequestParam("playSeq") int playSeq) {
		   try {
		        // 서비스 호출하여 업데이트
		        int count = qnaService.qnaCount(playSeq);
		        
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
	
	
	
	@GetMapping("qnaSearch")
	public  ResponseEntity<ApiResponse<List<QnaDTO>>> qnaSearch(
			 @RequestParam("playSeq") int playSeq, 
		        @RequestParam("searchType") String searchType,
		        @RequestParam("keyword") String keyword) {
		 try {
        List<QnaDTO> list;
        System.out.println(searchType+keyword+"qnaSearch");
		if(searchType.equals("id")) {
        	list =qnaService.qnaSearchId(keyword,playSeq);
        }
        else if( searchType.equals("title")){
        	
        	list =qnaService.qnaSearchKey(keyword,playSeq);
        	
        }
        else {
        	  return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                      .body(new ApiResponse<>(400, "유효하지 않은 검색 타입", null));
        }
        

        if (!list.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(200, "성공", list));
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(404 , "QA 없음",  list));
        }

    } catch (Exception e) {
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(400, "오류", null));
    }
	
	
	}
	
}
