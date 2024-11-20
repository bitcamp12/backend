package com.example.demo.dao;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.dto.member.MemberDTO;

@Mapper
public interface MemberDAO {

	public int signUp(MemberDTO memberDTO);

	public int checkId(String id);

	
}
