package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.MemberDTO;
import com.example.demo.service.MemberService;

@RestController
@RequestMapping("api/member")
public class MemberController {
	
	@Autowired
	MemberService memberService;
	
    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody MemberDTO memberDTO) {
        System.out.println("Received data: " + memberDTO);
        
        memberService.signUp(memberDTO);
        
        return ResponseEntity.ok("회원가입을 축하드립니다");
    }

	@PostMapping("login")
	public String login(Model model) {
        return "login";  
    }
	

}
