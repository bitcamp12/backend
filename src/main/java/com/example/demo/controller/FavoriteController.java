package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.FavoriteService;


@RestController
@RequestMapping(value="/api/favorite")
public class FavoriteController {
    
	@Autowired
	private FavoriteService favoriteService;
}
