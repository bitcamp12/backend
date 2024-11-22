package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.ReviewBefore;

//JPA용 Repository
@Repository
public interface ReviewBeforeRepository extends JpaRepository<ReviewBefore, Integer> {
 // 필요한 경우, 커스텀 쿼리 메서드 선언

}