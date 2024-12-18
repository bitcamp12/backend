package com.example.demo.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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


	Page<Book> findByMemberAndPayDateBetween(Member member, LocalDateTime startOfMonth, LocalDateTime endOfMonth,
			Pageable pageable);

//    @Query(value = "SELECT * FROM book WHERE member_seq = :memberSeq " +
//            "AND YEAR(pay_date) = :year " +
//            "AND MONTH(pay_date) = :month",
//    countQuery = "SELECT count(*) FROM book WHERE member_seq = :memberSeq " +
//                 "AND YEAR(pay_date) = :year " +
//                 "AND MONTH(pay_date) = :month", 
//    nativeQuery = true)
//    Page<Book> findByMemberAndPayDateYearAndPayDateMonth(@Param("memberSeq") Long memberSeq,
//                                                  @Param("year") int year,
//                                                  @Param("month") int month,
//                                                  Pageable pageable);
}

