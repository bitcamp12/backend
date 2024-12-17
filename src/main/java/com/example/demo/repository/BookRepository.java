package com.example.demo.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.dto.CheckMyBookDTO;
import com.example.demo.entity.Book;
import com.example.demo.entity.Member;

//JPA용 Repository
@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
	// 필요한 경우, 커스텀 쿼리 메서드 선언

	Page<Book> findByMember(Member member, Pageable pageable);

	Page<Book> findByMemberAndPayDateYearAndPayDateMonth(Member member, String year, String month, Pageable pageable);

}

