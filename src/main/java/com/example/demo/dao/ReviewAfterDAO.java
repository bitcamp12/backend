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
		        Now()                  
		    )
		""")
		int reviewAWrite(@Param("playSeq") int playSeq,
		                 @Param("memberSeq") int memberSeq,
		                 @Param("content") String content,
		                 @Param("rating") int rating);
	
	

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
	ReviewAfterDTO getReviewA(int  reviewAfterSeq);

	 
	 
	 @Update("""
			    UPDATE review_after
			    SET 
			        content = #{content}, 
			        rating = #{rating},
			        created_date = CURRENT_TIMESTAMP
			    WHERE 
			        review_after_seq = #{reviewAfterSeq}
			""")
			int reviewAUpdate(@Param("reviewAfterSeq") int reviewAfterSeq,
			                  @Param("content") String content, 
			                  @Param("rating") int rating);
	 
	 

	 @Delete("""
		        DELETE FROM review_after
		        WHERE review_after_seq = #{reviewAfterSeq}
		    """)
	int reviewADelete(int reviewAfterSeq);


	 @Select("""
			    SELECT COUNT(*)
			    FROM review_after
			    WHERE play_seq = #{playSeq}
			""")
			int reviewACount(int playSeq);

	 @Select("""
			    SELECT AVG(rating) AS avg_rating
			    FROM review_after
			    WHERE play_seq = #{playSeq}
			""")
			Float ReviewAAvg(int playSeq);


	 @Select("""
		        SELECT * FROM  review_after
		        WHERE play_seq = #{playSeq}
		        ORDER BY rating DESC
		    """)
	List<ReviewAfterDTO> getReviewAListStar(int playSeq);


	


	 


	// 아이디로 검색 - 날짜 순
	 @Select("""
	     SELECT *
	     FROM review_after
	     WHERE member_seq LIKE CONCAT('%', #{keyword}, '%')
	       AND play_seq = #{playSeq}
	     ORDER BY created_date DESC
	 """)
	 List<ReviewAfterDTO> ReviewASearchIdDate(@Param("keyword") String keyword, @Param("playSeq") int playSeq);

	 // 아이디로 검색 - 별점 순
	 @Select("""
	     SELECT *
	     FROM review_after
	     WHERE member_seq LIKE CONCAT('%', #{keyword}, '%')
	       AND play_seq = #{playSeq}
	     ORDER BY rating DESC
	 """)
	 List<ReviewAfterDTO> ReviewASearchIdRating(@Param("keyword") String keyword, @Param("playSeq") int playSeq);

	 // 내용으로 검색 - 날짜 순
	 @Select("""
	     SELECT *
	     FROM review_after
	     WHERE content LIKE CONCAT('%', #{keyword}, '%')
	       AND play_seq = #{playSeq}
	     ORDER BY created_date DESC
	 """)
	 List<ReviewAfterDTO> ReviewASearchDate(@Param("keyword") String keyword, @Param("playSeq") int playSeq);

	 // 내용으로 검색 - 별점 순
	 @Select("""
	     SELECT *
	     FROM review_after
	     WHERE content LIKE CONCAT('%', #{keyword}, '%')
	       AND play_seq = #{playSeq}
	     ORDER BY rating DESC
	 """)
	 List<ReviewAfterDTO> ReviewASearchRating(@Param("keyword") String keyword, @Param("playSeq") int playSeq);

	

	
}
