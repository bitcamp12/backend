package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.PlayService;


@RestController
@RequestMapping(value="/api/plays")
public class PlayController {
    
	@Autowired
	private PlayService playService;
}