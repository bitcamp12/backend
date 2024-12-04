package com.example.demo.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.example.demo.dto.MemberDTO;
import com.example.demo.dto.ReplyDTO;

@Mapper
public interface ReplyDAO {

	@Select("""
			
			select * from reply
			where qna_seq=#{qnaSeq}
			""")
	ReplyDTO getReply(@Param("qnaSeq") String qnaSeq);

	
}
