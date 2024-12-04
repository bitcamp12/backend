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
			SELECT 
				p.play_seq,
				p.name,
				p.price,
				p.start_time AS start_date,
				p.end_time AS end_date,
				p.image_file_name,
				p.image_original_file_name,
				p.address,
				pt.start_time,
				pt.end_time,
				pt.start_dis_time,
				pt.end_dis_time,
				pt.min_rate,
				pt.max_rate,
				pt.target_date
			FROM play p
			INNER JOIN play_time_table pt ON p.play_seq = pt.play_seq
			""")
	List<PlayDiscountDTO> getPlayWithDiscount();

	List<PlayDiscountDTO> orderDiscountedPlay(List<PlayDiscountDTO> discountedPlay);
	
}
