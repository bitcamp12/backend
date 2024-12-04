package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.Member;

//JPA용 Repository
@Repository
public interface MemberRepository extends JpaRepository<Member, Integer> {

	boolean existsById(String id);
 // 필요한 경우, 커스텀 쿼리 메서드 선언

	int countByNameAndEmail(String name, String email);

	int countByNameAndEmailAndId(String name, String email, String id);

   
    List<Member> findByNameAndEmail( String name,String email);

	int countByNameAndPhone(String name, String phoneNum);

	int countByNameAndPhoneAndId(String name, String phoneNum, String id);
	

	

	int countByIdAndPassword(String id, String password);

	Member findById(String id);

	List<Member> findByNameAndPhone(String name, String phone);


}

