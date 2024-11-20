package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.member.IdCheckDTO;
import com.example.demo.dto.member.MemberDTO;
import com.example.demo.entity.Member;
import com.example.demo.service.MemberService;
import com.example.demo.util.ApiResponse;

@RestController
@RequestMapping("api/members")
public class MemberController {
	
	@Autowired
	MemberService memberService;
	
    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<Member>> signUp(@RequestBody MemberDTO memberDTO) {
        System.out.println("Received data: " + memberDTO);
        try {
        	int result = memberService.signUp(memberDTO);
	        if(result == 1) {
	        	return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(200, "회원가입을 축하드립니다.", null));
	        }
	    }catch(Exception e) {
	    	e.printStackTrace();
        	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(400, "회원가입을 실패했습니다.", null));
        }
        
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(200, "success", null));
    }
    
    @PostMapping("/checkId")
    public ResponseEntity<ApiResponse<Member>> checkId(@RequestBody IdCheckDTO dto) {
        System.out.println("Received data: " + dto.getId());
        try {
            int result = memberService.checkId(dto.getId());
            System.out.println(result);
            if (result == 1) {
                return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(200, "exist", null));
            } else if (result == 0) {
                return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(200, "non_exist", null));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(400, "오류", null));
        }

        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(200, "non_exist", null));
    }


	@PostMapping("login")
	public String login(Model model) {
        return "login";  
    }
	

}
