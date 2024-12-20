package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.dto.PlayDTO;
import com.example.demo.entity.Play;

//JPA용 Repository
@Repository
public interface PlayRepository extends JpaRepository<Play, Integer> {


 // 필요한 경우, 커스텀 쿼리 메서드 선언


	List<Play> findByNameContaining(String name);
	

}