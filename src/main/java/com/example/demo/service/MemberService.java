package com.example.demo.service;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.dao.MemberDAO;
import com.example.demo.dto.CheckMyBookDTO;
import com.example.demo.dto.member.MemberDTO;
import com.example.demo.entity.Book;
import com.example.demo.entity.Member;
import com.example.demo.repository.AdminRepository;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.CheckMyBookRepository;
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

	@Autowired
	private CheckMyBookRepository checkMyBookRepository;

	@Autowired
	private BookRepository BookRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

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

			String encodedPassword = passwordEncoder.encode(member.getPassword());
			member.setPassword(encodedPassword); // 암호화된 비밀번호 저장
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
		return memberRepository.countByNameAndEmailAndId(name, email, id);
	}

	public String findIdByEmail2Entity(String name, String email) {
		System.out.println("아이디찾기");
		List<Member> list = memberRepository.findByNameAndEmail(name, email);
		if (list.size() >= 1) {
			return list.get(0).getId();
		} else {
			return "없음";
		}
	}

	public int findIdByPhoneEntity(String name, String phoneNum) {
		return memberRepository.countByNameAndPhone(name, phoneNum);
	}

	public int findPwdByPhoneEntity(String name, String phoneNum, String id) {
		return memberRepository.countByNameAndPhoneAndId(name, phoneNum, id);
	}

	public String findIdPhoneEntity(String name, String phone) {
		System.out.println("아이디찾기");
		List<Member> list = memberRepository.findByNameAndPhone(name, phone);
		if (list.size() >= 1) {
			return list.get(0).getId();
		} else {
			return "없음";
		}
	}

	@Transactional
	public int updatePwdEntity(String id, String password) {
		
        try {
            Member member = memberRepository.findById(id);
            
            String encodedPassword = passwordEncoder.encode(password);
            member.setPassword(encodedPassword);  // 암호화된 비밀번호 저장
            
            memberRepository.save(member);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0; // 실패
        }
	}




	public int LoginEntity(String id, String password) {

		// 사용자 ID로 회원 정보 조회
		Member member = memberRepository.findById(id);

		System.out.println("로그인엔티티");
		if (member != null && passwordEncoder.matches(password, member.getPassword())) {
			return 1; // 비밀번호가 일치하는 경우 로그인 성공
		}

		return 0; // 비밀번호가 일치하지 않거나 회원이 존재하지 않는 경우

	}

	public List<CheckMyBookDTO> checkMyBook(String id) {
		return memberDAO.checkMyBook(id);
	}


	public List<CheckMyBookDTO> checkBookingsByDate(Map<String, Object> map) {
		return memberDAO.checkBookingsByDate(map);
	}

	public Page<Book> checkMyBookPagination(String id, int currentPage, int pageSize) {
		Pageable pageable = PageRequest.of(currentPage, pageSize);
		System.out.println("[MemberService]checkMyBookPagination : " + pageable);
		// System.out.println("[MemberService]checkMyBookPagination : " +
		// checkMyBookRepository.findByMemberId(id, pageable));
		// return checkMyBookRepository.findByMemberId(id, pageable);

		Member member = memberRepository.findById(id); // select * from member where id = ' '

		Page<Book> books = BookRepository.findByMember(member, pageable); // select * from book where member_seq = ?

		return books;
	}

	public Page<Book> checkMyBookPagination(String id, String year, String month, int currentPage, int pageSize) {
		Pageable pageable = PageRequest.of(currentPage, pageSize);
		System.out.println("[MemberService]checkMyBookPagination : " + pageable);

		Member member = memberRepository.findById(id); // select * from member where id = ' '

		YearMonth yearMonth = YearMonth.of(Integer.parseInt(year), Integer.parseInt(month));

		// 해당 월의 첫날과 마지막 날을 구해서 between으로 찾기
		LocalDateTime startOfMonth = yearMonth.atDay(1).atStartOfDay();
		LocalDateTime endOfMonth = yearMonth.atEndOfMonth().atTime(23, 59, 59, 999999999);

		//findByMemberAndPayDateBetween(member, startOfMonth, endOfMonth);

		Page<Book> books = BookRepository.findByMemberAndPayDateBetween(member, startOfMonth, endOfMonth, pageable);
				//BookRepository.findByMember(member, pageable); // select * from book where member_seq = ? and
																			// year(pay_date) = ? and month(pay_date) =
																			// ?

		return books;
	}

	/*
	 * // 페이징 예약 확인 public Page<CheckMyBook> checkMyBookPagination(int currentPage,
	 * int pageSize) { Pageable pageable = PageRequest.of(currentPage, pageSize);
	 * System.out.println("[MemberService]checkMyBookPagination : " + pageable);
	 * System.out.println("[MemberService]checkMyBookPagination findeAll()  : " +
	 * checkMyBookRepository.findAll(pageable)); return
	 * checkMyBookRepository.findAll(pageable); }
	 */


}
