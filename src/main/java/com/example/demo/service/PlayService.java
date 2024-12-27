package com.example.demo.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.dao.PlayDAO;
import com.example.demo.dto.PlayDTO;
import com.example.demo.dto.PlayDiscountDTO;
import com.example.demo.entity.Play;
import com.example.demo.repository.PlayRepository;
import com.example.demo.util.ApiResponse;

@Service
public class PlayService {
	 

	@Autowired
	private PlayDAO playDAO;
	
	@Autowired
	private PlayRepository playRepository;

	private List<PlayDiscountDTO> cachedDiscountedPlays;

	@Cacheable(value = "play", key = "#playSeq != null ? #playSeq : '0'", unless = "#result == null")
	public PlayDTO getPlayOne(String playSeq) {
	    System.out.println("Fetching playSeq from DB: " + playSeq);
	    PlayDTO playDTO = playDAO.getPlayOne(playSeq);
	    System.out.println("Returned playDTO: " + playDTO);
	    return playDTO;  
	}
	

	//민웅 사용자 메인 페이지
	@Cacheable(value = "getPlayAll", key = "#page + '-' + #size")
	public List<PlayDTO> getPlayAll(int page, int size) {
		 System.out.println("Fetching random plays from DB...getPlayAll");
		int offset = (page - 1) * size;
		return playDAO.getPlayAll(offset, size);
	}

	public List<PlayDTO> searchList(String name) {
		return playDAO.searchList(name);
	}
	
	//메인 페이지 이미지 불러오는 함수
	@Cacheable(value = "PlayRandom")
    public List<PlayDTO> getPlayRandom() {
		 System.out.println("Fetching random plays from DB...PlayRandom");
        return playDAO.getPlayRandom();
    }

	public List<PlayDiscountDTO> getPlaySale() {
        return cachedDiscountedPlays;
    }
	
	@Cacheable(value = "searchListEntity", key = "#name != null ? #name : ' '")
	public List<Play> searchListEntity(String name) {
		 System.out.println("Fetching random plays from DB...searchListEntity");
		System.out.println(name+"**entity");
		List<Play> list = playRepository.findByNameContaining(name);
		
		 if (!list.isEmpty()) {
		        return list;
		   } else {
		        return new ArrayList<>(); 
		  }
	}
	@Cacheable(value = "getPlaysEndingSoon", key = "#page + '-' + #size")
	public List<PlayDTO> getPlaysEndingSoon(int page, int size) {
		 System.out.println("Fetching random plays from DB...getPlaysEndingSoon");
		int offset = (page - 1) * size;
		return playDAO.getPlaysEndingSoon(offset, size);
	}
	@Cacheable(value = "getPlaysComingSoon", key = "#page + '-' + #size")
	public List<PlayDTO> getPlaysComingSoon(int page, int size) {
		 System.out.println("Fetching random plays from DB...getPlaysEndingSoon");
		int offset = (page - 1) * size;
		return playDAO.getPlaysComingSoon(offset, size);
	}
	@Cacheable(value = "getPlaysLimited", key = "#page + '-' + #size")
	public List<PlayDTO> getPlaysLimited(int page, int size) {
		 System.out.println("Fetching random plays from DB...getPlaysLimited");
		int offset = (page - 1) * size;
		return playDAO.getPlaysLimited(offset, size);
	}


}