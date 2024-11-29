package com.example.demo.controller;

import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.FavoriteDTO;
import com.example.demo.entity.Favorite;
import com.example.demo.entity.ReviewAfter;
import com.example.demo.service.FavoriteService;
import com.example.demo.service.MemberService;
import com.example.demo.util.ApiResponse;

import jakarta.servlet.http.HttpSession;


@RestController
@RequestMapping(value="/api/favorites")
public class FavoriteController {
	@Autowired
	MemberService memberService;
    
	@Autowired
	private FavoriteService favoriteService;
	
	@PostMapping("favorites")
	public  ResponseEntity<ApiResponse<Favorite>> favoritesInsert(@RequestParam("playSeq") int playSeq,
			HttpSession session) {
		
		String userId=(String) session.getAttribute("id");
		System.out.println(userId);
		try {
			if(userId==null) {
				 return   ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(403 , "로그인안함", null));
			}
			else {
				int memberSeq=memberService.getMemberSeq(userId);
				System.out.println(memberSeq+"memberSeq");
//				
					System.out.println(memberSeq+"  "+playSeq);
				 int result=favoriteService.favoritesInsert(playSeq,memberSeq);
				 System.out.println(result+"result");
				 
				 if(result==1) {
					 return  ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(200, "성공", null));
				 }
				 else { 
					 return   ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(404 , "실패", null));
				 } 
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(400, "오류", null));
		}
	}
	
	@GetMapping("favorites")
	public  ResponseEntity<ApiResponse<Favorite>> favoritesCheck(@RequestParam("playSeq") int playSeq,
			HttpSession session) {
		
		String userId=(String) session.getAttribute("id");
		try {
			if(userId==null) {
				 return   ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(403 , "로그인안함", null));
			}
			else {
				int memberSeq=memberService.getMemberSeq(userId);
				
	//세션 구할거임
	 int result=favoriteService.favoritesCheck(playSeq,memberSeq);
	
	 if(result==1) {
		 return  ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(200, "데이터 존재", null));
	 }
	 else { 
		 return   ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(404 , "데이터 없음", null));
	 } 
			
			}} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(400, "오류", null));
		}
	}
	
	@DeleteMapping("favorites")
	public  ResponseEntity<ApiResponse<Favorite>> favoritesDelete(@RequestParam("playSeq") int playSeq,
			HttpSession session) {
		String userId=(String) session.getAttribute("id");
		try {
			if(userId==null) {
				 return   ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(403 , "로그인안함", null));
			}
			else {
				int memberSeq=memberService.getMemberSeq(userId);
//			
	//세션 구할거임
	 int result=favoriteService.favoritesDelete(playSeq,memberSeq);
	
	 if(result==1) {
		 return  ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(200, "삭제완료", null));
	 }
	 else { 
		 return   ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(404 , "삭제 실패", null));
	 } 
			
		}} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(400, "오류", null));
		}
	}
	
}
