package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Play;
import com.example.demo.entity.PlayTimeTable;

//JPA용 Repository
@Repository
public interface PlayTimeTableRepository extends JpaRepository<PlayTimeTable, Integer> {
 // 필요한 경우, 커스텀 쿼리 메서드 선언

}