package com.example.demo.dao;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.demo.dto.PlayDiscountDTO;
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
				p.age_limit,
				pt.start_time,
				pt.end_time,
				pt.start_dis_time,
				pt.end_dis_time,
				pt.min_rate,
				pt.max_rate,
				pt.target_date,
				pt.play_time_table_seq
			FROM play p
			INNER JOIN play_time_table pt ON p.play_seq = pt.play_seq
			""")
	List<PlayDiscountDTO> getPlayWithDiscount();

	@Update("""
			UPDATE play_time_table
			SET discount_rate = #{discountRate},
				discounted_price = #{discountedPrice}
			WHERE play_time_table_seq = #{playTimeTableSeq}
		""")	
	void updateDiscounts(PlayTimeTableDTO playTimeTableDTO);
										
}
