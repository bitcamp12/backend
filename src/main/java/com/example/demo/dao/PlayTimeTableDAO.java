package com.example.demo.dao;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.example.demo.dto.PlayTimeTableDTO;
import com.example.demo.dto.member.MemberDTO;

@Mapper
public interface PlayTimeTableDAO {

	@Select("""
		    select * from play_time_table
		    where play_seq = #{playSeq} 
		    and target_date = #{targetDate}
		    """)
		List<PlayTimeTableDTO> playTimeTables(@Param("playSeq") int playSeq, 
		                                      @Param("targetDate") String targetDate);


	
}
