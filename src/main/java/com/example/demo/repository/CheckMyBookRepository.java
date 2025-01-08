package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.CheckMyBook;

@Repository
public interface CheckMyBookRepository extends PagingAndSortingRepository<CheckMyBook, String>{

// 마이페이지- 예약현황 페이지
	//Page<CheckMyBook> findByMemberId(String id, Pageable pageable);
	@Query(value = "SELECT * FROM booking_status_view WHERE member_id = ?1", nativeQuery = true)
	List<CheckMyBook> findByMemberId(String id);
}
