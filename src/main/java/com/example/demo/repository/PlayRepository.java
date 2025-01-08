package com.example.demo.repository;

import java.time.LocalDateTime;
import java.util.List;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.dto.PlayDTO;
import com.example.demo.entity.Play;

import io.lettuce.core.dynamic.annotation.Param;

//JPA용 Repository
@Repository
public interface PlayRepository extends JpaRepository<Play, Integer> {


 // 필요한 경우, 커스텀 쿼리 메서드 선언


	List<Play> findByNameContaining(String name);

	Page<Play> findByPriceGreaterThanEqual(int price, Pageable pageable);

	Page<Play> findByStartTimeAfter(LocalDateTime currentTime, Pageable pageable);

	Page<Play> findByEndTimeBetween(LocalDateTime start, LocalDateTime end, Pageable pageable);
	

}