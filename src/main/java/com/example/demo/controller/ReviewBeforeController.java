package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.ReviewBeforeService;


@RestController
@RequestMapping(value="/api/reviewAfters")
public class ReviewBeforeController {
    
	@Autowired
	private ReviewBeforeService reviewBeforeService;
}
