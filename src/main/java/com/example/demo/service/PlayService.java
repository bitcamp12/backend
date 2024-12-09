package com.example.demo.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
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

	public PlayDTO getPlayOne(String playSeq) {
		PlayDTO playDTO=playDAO.getPlayOne(playSeq);
		return playDTO;     

	}

	//민웅 사용자 메인 페이지
	public List<PlayDTO> getPlayAll(int page, int size) {
		int offset = (page - 1) * size;
		return playDAO.getPlayAll(offset, size);
	}

	public List<PlayDTO> searchList(String name) {
		return playDAO.searchList(name);
	}

    public List<PlayDTO> getPlayRandom() {
        return playDAO.getPlayRandom();
    }

	public List<PlayDiscountDTO> getPlaySale() {
        return cachedDiscountedPlays;
    }

	public List<Play> searchListEntity(String name) {
		System.out.println(name+"**entity");
		List<Play> list = playRepository.findByNameContaining(name);
		
		 if (!list.isEmpty()) {
		        return list;
		   } else {
		        return new ArrayList<>(); 
		  }
	}

	public List<PlayDTO> getPlaysEndingSoon(int page, int size) {
		int offset = (page - 1) * size;
		return playDAO.getPlaysEndingSoon(offset, size);
	}

	public List<PlayDTO> getPlaysComingSoon(int page, int size) {
		int offset = (page - 1) * size;
		return playDAO.getPlaysComingSoon(offset, size);
	}

	public List<PlayDTO> getPlaysLimited(int page, int size) {
		int offset = (page - 1) * size;
		return playDAO.getPlaysLimited(offset, size);
	}


}