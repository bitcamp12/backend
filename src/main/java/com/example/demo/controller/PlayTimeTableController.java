package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.PlayService;
import com.example.demo.service.PlayTimeTableService;


@RestController
@RequestMapping(value="/api/playTimeTable")
public class PlayTimeTableController {
    
	@Autowired
	private PlayTimeTableService playTimeTableService;
}
