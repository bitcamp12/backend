package com.example.demo.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.example.demo.dto.member.MemberDTO;

import com.example.demo.dto.PlayDTO;


@Mapper
public interface PlayDAO {

	@Select("""
			selete * from play
			where play_seq=#{playSeq}
			""")
	PlayDTO getPlayOne(String playSeq);

	
}
