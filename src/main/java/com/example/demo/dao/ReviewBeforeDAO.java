package com.example.demo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;


import com.example.demo.dto.ReviewBeforeDTO;



@Mapper
public interface ReviewBeforeDAO {

	 
	    @Insert("""
	            INSERT INTO review_before (
	                play_seq,
	                member_seq,
	                content,
	                created_date
	            ) VALUES (
	                #{playSeq},               
	                #{memberSeq},             
	                #{content},               
	                NOW()                     
	            )
	            """)
	    int reviewBWrite(@Param("playSeq") int playSeq, 
                @Param("memberSeq") int memberSeq, 
                @Param("content") String content);

	    // 리뷰 목록 조회 (별점 제외)
	    @Select("""
	            SELECT * FROM review_before
	            WHERE play_seq = #{playSeq}
	            ORDER BY created_date DESC
	            """)
	    List<ReviewBeforeDTO> getReviewBList(int playSeq);

	    // 특정 리뷰 조회 (별점 제외)
	    @Select("""
	            SELECT * FROM review_before
	            WHERE review_before_seq = #{reviewBeforeSeq}
	            """)
	    ReviewBeforeDTO getReviewB(int reviewBeforeSeq);

	    // 리뷰 수정 (별점 제외)
	    @Update("""
	            UPDATE review_before
	            SET 
	                content = #{content},
	                created_date = CURRENT_TIMESTAMP
	            WHERE 
	                review_before_seq = #{reviewBeforeSeq}
	            """)
	    int reviewBUpdate(@Param("reviewBeforeSeq") int reviewBeforeSeq, 
	    					@Param("content") String content);

	    // 리뷰 삭제
	    @Delete("""
	            DELETE FROM review_before
	            WHERE review_before_seq = #{reviewBeforeSeq}
	            """)
	    int reviewBDelete(int reviewBeforeSeq);

	

	
}
