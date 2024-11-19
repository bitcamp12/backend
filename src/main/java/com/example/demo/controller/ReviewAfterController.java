package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.ReviewAfterService;


@RestController
@RequestMapping(value="/api/reviewAfter")
public class ReviewAfterController {
    
	@Autowired
	private ReviewAfterService reviewAfterService;
}
