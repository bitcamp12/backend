package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.QnaDAO;

@Service
public class QnaService {

	@Autowired
	private QnaDAO qnaDAO;
}
