package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.TheaterDAO;
import com.example.demo.dto.TheaterDTO;

@Service
public class TheaterService {

	@Autowired
	private TheaterDAO theaterDAO;

    public List<TheaterDTO> getTheaterInfo() {
        return theaterDAO.getTheaterInfo();
    }
}
