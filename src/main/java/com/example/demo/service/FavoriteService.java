package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.FavoriteDAO;

@Service
public class FavoriteService {

	@Autowired
	private FavoriteDAO favoriteDAO;
}
