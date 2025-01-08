package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.FavoriteDAO;

@Service
public class FavoriteService {

	@Autowired
	private FavoriteDAO favoriteDAO;

	public int favoritesInsert(int playSeq, int memberSeq) {
		// TODO Auto-generated method stub
		return favoriteDAO.favoritesInsert(playSeq,memberSeq);
	}

	public int favoritesCheck(int playSeq, int memberSeq) {
		// TODO Auto-generated method stub
		return favoriteDAO.favoritesCheck(playSeq,memberSeq);
	}

	public int favoritesDelete(int playSeq, int memberSeq) {
		// TODO Auto-generated method stub
		return favoriteDAO.favoritesDelete(playSeq,memberSeq);
	}
}
