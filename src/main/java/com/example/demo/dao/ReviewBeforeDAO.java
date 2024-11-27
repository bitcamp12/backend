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

	    @Select("""
	    	    SELECT rb.*, m.id
	    	    FROM review_before rb
	    	    JOIN member m ON rb.member_seq = m.member_seq
	    	    WHERE rb.play_seq = #{playSeq}
	    	    ORDER BY rb.created_date DESC
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

	    
	    
	    @Select("""
			    SELECT COUNT(*)
			   FROM review_before
			    WHERE play_seq = #{playSeq}
			""")
		int ReviewBcount(int playSeq);

	    
	    
	    
	 // 아이디로 검색 - 날짜 순
	    @Select("""
	        SELECT rb.*, m.id
	        FROM review_before rb
	        JOIN member m ON rb.member_seq = m.member_seq
	        WHERE rb.member_seq LIKE CONCAT('%', #{keyword}, '%')
	          AND rb.play_seq = #{playSeq}
	        ORDER BY rb.created_date DESC
	    """)
	    List<ReviewBeforeDTO> ReviewBSearchId(@Param("keyword") String keyword, @Param("playSeq") int playSeq);

	    // 내용으로 검색 - 날짜 순
	    @Select("""
	        SELECT rb.*, m.id
	        FROM review_before rb
	        JOIN member m ON rb.member_seq = m.member_seq
	        WHERE rb.content LIKE CONCAT('%', #{keyword}, '%')
	          AND rb.play_seq = #{playSeq}
	        ORDER BY rb.created_date DESC
	    """)
	    List<ReviewBeforeDTO> ReviewBSearchKey(@Param("keyword") String keyword, @Param("playSeq") int playSeq);

	

	
}
