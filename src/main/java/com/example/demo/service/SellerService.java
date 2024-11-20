package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.SellerDAO;
import com.example.demo.dto.SellerDTO;

@Service
public class SellerService {
	@Autowired 
	SellerDAO sellerDAO;
	
	public void playReg(SellerDTO sellerDTO) {
		sellerDAO.playReg(sellerDTO);
	}
}
