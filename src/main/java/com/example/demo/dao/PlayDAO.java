package com.example.demo.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.example.demo.dto.member.MemberDTO;

import com.example.demo.dto.PlayDTO;
import com.example.demo.dto.PlayDiscountDTO;


@Mapper
public interface PlayDAO {

	@Select("""
			 SELECT   p.*, t.discounted_price,t.discount_rate,t.start_time,t.target_date
    FROM   play p
    LEFT JOIN play_time_table t  ON  p.play_seq = t.play_seq
    WHERE  p.play_seq = #{playSeq}
			""")
	PlayDTO getPlayOne(String playSeq);


	//민웅 사용자 메인 페이지
	@Select("""
			SELECT * FROM play
			LIMIT #{size} OFFSET #{offset}
			""")
	List<PlayDTO> getPlayAll(@Param("offset") int offset, @Param("size") int size);
	
	
	@Select("""
	        SELECT * FROM play WHERE name LIKE CONCAT('%', #{name}, '%')
	        """)
	List<PlayDTO> searchList(String name);


	@Select("""
			SELECT * FROM play
			ORDER BY RAND() LIMIT 10;
			""")
    List<PlayDTO> getPlayRandom();

}
