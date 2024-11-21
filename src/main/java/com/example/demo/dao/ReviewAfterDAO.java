package com.example.demo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;


import com.example.demo.dto.member.MemberDTO;

import com.example.demo.dto.ReviewAfterDTO;

@Mapper
public interface ReviewAfterDAO {

	//리뷰작성
	@Insert("""
	        INSERT INTO review_after (
	            play_seq,
	            member_seq,
	            content,
	            rating,
	            created_date
	        ) VALUES (
	            #{playSeq},               
	            #{memberSeq},             
	            #{content},               
	            #{rating},               
	            SYSDATE                   
	        )
	    """)
	void reviewAWrite(int playSeq, int memberSeq, String content, String rating);

	 @Select("""
		        SELECT * FROM  review_after
		        WHERE play_seq = #{playSeq}
		        ORDER BY created_date DESC
		    """)
		    List<ReviewAfterDTO> getReviewAList(@Param("playSeq") int playSeq);
	 
	 @Select("""
		        SELECT * FROM  review_after
		        WHERE review_after_seq = #{reviewAfterSeq}
		    """)
	ReviewAfterDTO getReviewA(String reviewAfterSeq);

	 @Update("""
		        UPDATE review_after
		        SET 
		            content = #{content}, 
		            rating = #{rating},
		            created_date = CURRENT_TIMESTAMP
		        WHERE 
		            review_after_seq = #{reviewAfterSeq}
		    """)
	void reviewAUpdate(String reviewAfterSeq, String content, String rating);

	 @Delete("""
		        DELETE FROM review_after
		        WHERE review_after_seq = #{reviewAfterSeq}
		    """)
	void reviewADelete(String reviewAfterSeq);
	

	
}
