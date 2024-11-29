package com.example.demo.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.MemberDAO;
import com.example.demo.dto.CheckMyBookDTO;
import com.example.demo.dto.member.MemberDTO;

@Service
public class MemberService {

	@Autowired 
	MemberDAO memberDAO;
	
	public String test() {
		return "테스트입니다.";
	}

	public int signUp(MemberDTO memberDTO) {
		return memberDAO.signUp(memberDTO);
			
	}

	public int checkId(String id) {
		return memberDAO.checkId(id);
	}
	

	public String findIdPhone(Map<String, String> map) {
		return memberDAO.findIdPhone(map);
	}

	public int findIdByEmail(Map<String, String> map) {
		return memberDAO.findIdByEmail(map);
	}

	public String findIdByEmail2(Map<String, String> map) {
		return memberDAO.findIdByEmail2(map);
	}

	public MemberDTO getUserInfo(String id) {
		return memberDAO.getUserInfo(id);
	}

	public void modifyUserInfo(MemberDTO modifiedData) {
		memberDAO.modifyUserInfo(modifiedData);
	}

	public int findIdByPhone(Map<String, String> map) {
		return memberDAO.findIdByPhone(map);
	}

	public int updatePwd(Map<String, String> map) {
		return memberDAO.updatePwd(map);
	}

	public int Login(Map<String, String> map) {
		return memberDAO.Login(map);
	}

	public void infoWithdrawal(String id) {
		memberDAO.infoWithdrawal(id);
	}

	


	public int getMemberSeq(String userId) {
		return memberDAO.getMemberSeq(userId);
	}

	public int findPwdByPhone(Map<String, String> map) {
		return memberDAO.findPwdByPhone(map);
	}

	public int findPwdByEmail(Map<String, String> map) {
		return memberDAO.findPwdByEmail(map);
	}

	public List<CheckMyBookDTO> checkMyBook(String id) {
		return memberDAO.checkMyBook(id);
	}




}
