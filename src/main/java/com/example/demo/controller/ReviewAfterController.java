package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.ReviewAfterDTO;
import com.example.demo.service.MemberService;
import com.example.demo.service.ReviewAfterService;

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
	public void reviewWriteA(@RequestParam("playSeq") int playSeq,
							  @RequestParam("content") String content,
							  @RequestParam("rating") String rating,
							  HttpSession httpSession,Model model) {
		
		int memberSeq=1;
//				memberService.getMemberSeq();
		//세션 구할거임
		 reviewAfterService.reviewAWrite(playSeq,memberSeq,content,rating);
		
	}
	
	//리뷰 list 출력
	@GetMapping("getReviewAList")
	public List<ReviewAfterDTO> getReviewAList(@RequestParam("playSeq") int playSeq) {
		
		return reviewAfterService.getReviewAList(playSeq);
	}
	
	//리뷰 list 출력
		@GetMapping("getReviewA")
		public ReviewAfterDTO getReviewA(@RequestParam("reviewAfterSeq") String reviewAfterSeq) {
			
			return reviewAfterService.getReviewA(reviewAfterSeq);
		}
	
	
	//리뷰 수정
	@PostMapping("reviewAUpdate")
	public void reviewAUpdate(@RequestParam("reviewAfterSeq") String reviewAfterSeq,
								  @RequestParam("content") String content,
								  @RequestParam("rating") String rating,
								  Model model) {
		
		
		 reviewAfterService.reviewAUpdate(reviewAfterSeq,content,rating);
	}
	
	//리뷰 삭제
	@PostMapping("reviewADelete")
	public void reviewADelete(@RequestParam("reviewAfterSeq") String reviewAfterSeq) {
		
		reviewAfterService.reviewADelete(reviewAfterSeq);
		
	}
	
	
}
