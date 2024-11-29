package com.example.demo.repository;

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

    @Query("SELECT m.id FROM Member m WHERE m.name = :name AND m.email = :email")
    String findIdByNameAndEmail(@Param("name") String name, @Param("email") String email);

	int countByNameAndPhone(String name, String phoneNum);

	int countByNameAndPhoneAndId(String name, String phoneNum, String id);
	
	@Query("SELECT m.id FROM Member m WHERE m.name = :name AND m.phone = :phone")
	String findIdByNameAndPhone(@Param("name")String name,  @Param("phone")String phone);
	
	@Modifying  // 업데이트, 삭제 등 데이터 변경 쿼리 실행 시 필수
	@Transactional  // 데이터 변경이 트랜잭션 안에서 실행되도록 보장
	@Query("UPDATE Member m SET m.password = :password WHERE m.id = :id")
	int updatePasswordById(@Param("id") String id, @Param("password") String password);

	int countByIdAndPassword(String id, String password);


}

