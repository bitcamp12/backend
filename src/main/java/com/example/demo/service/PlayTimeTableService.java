package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.PlayTimeTableDAO;

@Service
public class PlayTimeTableService {

	@Autowired
	private PlayTimeTableDAO playTimeTableDAO;
}
