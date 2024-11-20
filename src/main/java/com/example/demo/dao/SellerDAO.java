package com.example.demo.dao;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.dto.SellerDTO;

@Mapper
public interface SellerDAO {
	public void playReg(SellerDTO sellerDTO);
}
