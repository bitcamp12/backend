package com.example.demo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;


import com.example.demo.dto.QnaDTO;




@Mapper
public interface QnaDAO {

	 // Q&A 작성
	 @Insert("""
		        INSERT INTO qna (play_seq, member_seq, title, content, created_date)
		        VALUES (#{playSeq}, #{memberSeq}, #{title}, #{content}, NOW())
		    """)
		    int qnaWrite(@Param("playSeq") int playSeq, 
		                 @Param("memberSeq") int memberSeq, 
		                 @Param("title") String title, 
		                 @Param("content") String content);

	// Q&A 목록 조회
	 @Select("""
	     SELECT q.*, m.id
	     FROM qna q
	     JOIN member m ON q.member_seq = m.member_seq
	     WHERE q.play_seq = #{playSeq}
	     ORDER BY q.created_date DESC
	     LIMIT #{size} OFFSET #{pages}
	 """)
	 List<QnaDTO> getQnaList(@Param("playSeq")int playSeq,@Param("pages") int pages ,@Param("size") int size);

    // 특정 Q&A 조회
    @Select("""
            SELECT * FROM qna
            WHERE qna_seq = #{qnaSeq}
            """)
    QnaDTO getQnaOne(int qnaSeq);

    @Update("""
            UPDATE qna
            SET title = #{title},
                content = #{content},
                created_date = CURRENT_TIMESTAMP  
            WHERE qna_seq = #{qnaSeq}
            """)
    int updateQna(@Param("qnaSeq") int qnaSeq, @Param("title") String title, @Param("content") String content);


    // Q&A 삭제
    @Delete("""
            DELETE FROM qna
            WHERE qna_seq = #{qnaSeq}
            """)
    int deleteQna(int qnaSeq);

    
    
    @Select("""
		    SELECT COUNT(*)
		    FROM qna
		    WHERE play_seq = #{playSeq}
		""")
	int qnaCount(int playSeq);

    
    
 // member_seq로 검색하여 Q&A 데이터 조회 (아이디 포함)
    @Select("""
        SELECT q.*, m.id
        FROM review_before q
        JOIN member m ON q.member_seq = m.member_seq
        WHERE q.member_seq LIKE CONCAT('%', #{keyword}, '%')
          AND q.play_seq = #{playSeq}
        ORDER BY q.created_date DESC
    """)
    List<QnaDTO> qnaSearchId(@Param("keyword") String keyword, @Param("playSeq") int playSeq);

    // 내용으로 검색하여 Q&A 데이터 조회 (아이디 포함)
    @Select("""
        SELECT q.*, m.id
        FROM review_before q
        JOIN member m ON q.member_seq = m.member_seq
        WHERE q.content LIKE CONCAT('%', #{keyword}, '%')
          AND q.play_seq = #{playSeq}
        ORDER BY q.created_date DESC
    """)
    List<QnaDTO> qnaSearchKey(@Param("keyword") String keyword, @Param("playSeq") int playSeq);

	
}
