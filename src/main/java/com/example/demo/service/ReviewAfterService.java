package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.ReviewAfterDAO;

@Service
public class ReviewAfterService {

	@Autowired
	private ReviewAfterDAO reviewAfterDAO;
}
