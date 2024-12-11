package com.example.demo.service;


import java.util.List;

import java.time.LocalDateTime;

import java.util.Map;

import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.MemberDAO;
import com.example.demo.dto.CheckMyBookDTO;
import com.example.demo.dto.member.MemberDTO;
import com.example.demo.entity.Member;
import com.example.demo.repository.AdminRepository;
import com.example.demo.repository.MemberRepository;

import jakarta.transaction.Transactional;

@Service
public class MemberService {

	@Autowired 
	MemberDAO memberDAO;
	
    @Autowired
    private MemberRepository memberRepository;
    
    @Autowired
    private AdminRepository adminRepository;
	
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


	 public int signUp(Member member) {
	        try {
//	        	member.setRegisterDate(LocalDateTime.now());
	            memberRepository.save(member); // 엔티티 저장
	            return 1; // 성공
	        } catch (Exception e) {
	            e.printStackTrace();
	            return 0; // 실패
	        }
	}

	public boolean checkIdEntity(String id) {
        // 회원 테이블에서 아이디가 존재하는지 확인
        boolean isMemberExist = memberRepository.existsById(id);
        
        // 관리자 테이블에서 아이디가 존재하는지 확인
        boolean isAdminExist = adminRepository.existsById(id);
        
        // 두 테이블에서 하나라도 존재하면 중복된 아이디로 처리
        return isMemberExist || isAdminExist;
	}

	public int findIdByEmailEntity(String name, String email) {
		return memberRepository.countByNameAndEmail(name, email);
	}

	public int findPwdByEmailEntity(String name, String email, String id) {
		return memberRepository.countByNameAndEmailAndId(name, email,id);
	}

	public String findIdByEmail2Entity(String name, String email) {
		System.out.println("아이디찾기"); 
		List<Member> list = memberRepository.findByNameAndEmail(name, email);
		if(list.size() >= 1) {
			return list.get(0).getId();
		}else {
			return "없음";
		}	
	}

	public int findIdByPhoneEntity(String name, String phoneNum) {
		return memberRepository.countByNameAndPhone(name, phoneNum);
	}

	public int findPwdByPhoneEntity(String name, String phoneNum, String id) {
		return memberRepository.countByNameAndPhoneAndId(name, phoneNum,id);
	}

	
	public String findIdPhoneEntity(String name, String phone) {
		System.out.println("아이디찾기"); 
		List<Member> list = memberRepository.findByNameAndPhone(name, phone);
		if(list.size() >= 1) {
			return list.get(0).getId();
		}else {
			return "없음";
		}
	}
		

	@Transactional
	public int updatePwdEntity(String id, String password) {
		
        try {
            Member member = memberRepository.findById(id);
            member.setPassword(password);
            memberRepository.save(member);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0; // 실패
        }
	}
        
		

	public int LoginEntity(String id, String password) {
		return memberRepository.countByIdAndPassword(id,password);


}

	public List<CheckMyBookDTO> checkMyBook(String id) {
		return memberDAO.checkMyBook(id);
	}

	public List<CheckMyBookDTO> checkBookingsByDate(Map<String, Object> map) {
		return memberDAO.checkBookingsByDate(map);
	}

	
}
