package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.PlayDAO;

@Service
public class PlayService {

	@Autowired
	private PlayDAO playDAO;
}
