package com.example.demo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.example.demo.dto.member.MemberDTO;

import com.example.demo.dto.PlayDTO;


@Mapper
public interface PlayDAO {

	@Select("""
			SELECT * FROM play WHERE play_seq = #{playSeq}
			""")
	PlayDTO getPlayOne(String playSeq);

	@Select("""
			    SELECT * FROM play
			    LIMIT #{size} OFFSET #{offset}
			""")
	List<PlayDTO> getPlayAll(@Param("offset") int offset, @Param("size") int size);


	@Select("""
			SELECT * FROM play
			ORDER BY RAND() LIMIT 10;
			""")
    List<PlayDTO> getPlayRandom();

	
}
