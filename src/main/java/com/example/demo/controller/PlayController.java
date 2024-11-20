package com.example.demo.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.PlayDTO;
import com.example.demo.service.PlayService;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping(value="/api/plays")
public class PlayController {
    
	@Autowired
	private PlayService playService;
	
	
	@GetMapping("getPlayOne")
	public  Map<String, Object> getPlayOne(@RequestParam("playSeq") String playSeq) {
		 
		 
		 Map<String, Object> response = playService.getPlayOne(playSeq);
		 
		return response;
	}
	
}
