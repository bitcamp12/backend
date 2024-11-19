package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.QnaService;


@RestController
@RequestMapping(value="/api/qna")
public class QnaController {
    
	@Autowired
	private QnaService qnaService;
}
