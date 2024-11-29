package com.example.demo.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.demo.dao.PlayDAO;
import com.example.demo.dto.PlayDTO;
import com.example.demo.entity.Play;
import com.example.demo.repository.PlayRepository;
import com.example.demo.util.ApiResponse;

@Service
public class PlayService {

	@Autowired
	private PlayDAO playDAO;
	
	
	@Autowired
	private PlayRepository playRepository;

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

	public List<PlayDTO> getPlaySale() {
		return playDAO.getPlaySale();
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
}
