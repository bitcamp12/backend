package com.example.demo.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.demo.dao.PlayDAO;
import com.example.demo.dto.PlayDTO;

@Service
public class PlayService {

	@Autowired
	private PlayDAO playDAO;

	public Map<String, Object> getPlayOne(String playSeq) {
		PlayDTO playDTO=playDAO.getPlayOne(playSeq);
		
		 // 응답을 담을 Map 생성
        Map<String, Object> response = new HashMap<>();

        if (playDTO != null) {
            // 성공적인 응답(200 OK)
            response.put("status", HttpStatus.OK.value());
            response.put("data", playDTO);
        } else {
            // 데이터가 없는 경우 (404 Not Found)
            response.put("status", "fail");
            response.put("message", HttpStatus.NOT_FOUND.value());
        }

        return response;

	}
}
