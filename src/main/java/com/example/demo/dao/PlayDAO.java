package com.example.demo.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

<<<<<<< HEAD
import com.example.demo.dto.member.MemberDTO;
=======
import com.example.demo.dto.MemberDTO;
import com.example.demo.dto.PlayDTO;
>>>>>>> 694caceb7fc25b86282f9e95ee11a1efcf0cfabe

@Mapper
public interface PlayDAO {

	@Select("""
			selete * from play
			where play_seq=#{playSeq}
			""")
	PlayDTO getPlayOne(String playSeq);

	
}
