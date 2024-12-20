package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Favorite;
import com.example.demo.entity.Member;
import com.example.demo.service.FavoriteService;
import com.example.demo.service.MemberService;
import com.example.demo.util.ApiResponse;
import com.example.demo.util.AuthenticationFacade;

import jakarta.servlet.http.HttpSession;


@RestController
@RequestMapping(value="/api/favorites")
public class FavoriteController {
	@Autowired
	MemberService memberService;
    
	@Autowired
	private FavoriteService favoriteService;

	@Autowired
	AuthenticationFacade authenticationFacade;

	@PostMapping("favorites")
	public  ResponseEntity<ApiResponse<Favorite>> favoritesInsert(@RequestParam("playSeq") int playSeq,
			HttpSession session) {
		

		Member member = authenticationFacade.getCurrentMember(); // jwt 인증시 로그인된 멤버 엔티티 정보획득

        System.out.println("현재로그인아이디"+member.getId());  // 아이디 가져오는예시 
		try {
			if(member.getId()==null) {
				 return   ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(403 , "로그인안함", null));
			}
			else {
				int memberSeq=memberService.getMemberSeq(member.getId());

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
		Member member = authenticationFacade.getCurrentMember(); // jwt 인증시 로그인된 멤버 엔티티 정보획득

        System.out.println("현재로그인아이디"+member.getId());  // 아이디 가져오는예시 String userId=(String) session.getAttribute("id");
		try {
			if(member.getId()==null) {
				 return   ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(403 , "로그인안함", null));
			}
			else {
				int memberSeq=memberService.getMemberSeq(member.getId());
				
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

		Member member = authenticationFacade.getCurrentMember(); // jwt 인증시 로그인된 멤버 엔티티 정보획득

        System.out.println("현재로그인아이디"+member.getId());  // 아이디 가져오는예시 
		try {
			if(member.getId()==null) {
				 return   ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(403 , "로그인안함", null));
			}
			else {
				int memberSeq=memberService.getMemberSeq(member.getId());
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
