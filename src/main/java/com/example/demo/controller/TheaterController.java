package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.ReviewBeforeService;
import com.example.demo.service.TheaterService;


@RestController
@RequestMapping(value="/api/theaters")
public class TheaterController {
    
	@Autowired
	private TheaterService reviewBeforeService;
	
}
