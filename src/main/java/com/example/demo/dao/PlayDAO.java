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
			SELECT * FROM play WHERE play_seq = #{playSeq}
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

    @Select("""
			SELECT * FROM play 
			WHERE end_time BETWEEN NOW() AND DATE_ADD(NOW(), INTERVAL 30 DAY)
			ORDER BY end_time ASC
			LIMIT #{size} OFFSET #{offset}
			""")
	List<PlayDTO> getPlaysEndingSoon(@Param("offset") int offset, @Param("size") int size);

	@Select("""
			SELECT * FROM play
			WHERE start_time > NOW()
			ORDER BY start_time ASC
			LIMIT #{size} OFFSET #{offset}
			""")
	List<PlayDTO> getPlaysComingSoon(@Param("offset") int offset, @Param("size") int size);

	@Select("""
			SELECT * FROM play
			WHERE price >= 60000
			ORDER BY price DESC
			LIMIT #{size} OFFSET #{offset}
			""")
	List<PlayDTO> getPlaysLimited(int offset, int size);

}
