package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.MemberDAO;
import com.example.demo.dto.MemberDTO;

@Service
public class MemberService {

	@Autowired 
	MemberDAO memberDAO;
	
	public String test() {
		return "테스트입니다.";
	}

	public void signUp(MemberDTO memberDTO) {
		memberDAO.signUp(memberDTO);
		
	}
}
