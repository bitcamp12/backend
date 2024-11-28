package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.PlayTimeTableDAO;
import com.example.demo.dto.PlayTimeTableDTO;

@Service
public class PlayTimeTableService {

	@Autowired
	private PlayTimeTableDAO playTimeTableDAO;

	public List<PlayTimeTableDTO> playTimeTables(int playSeq, String targetDate) {
		// TODO Auto-generated method stub
		return playTimeTableDAO.playTimeTables(playSeq,targetDate);
	}
}
