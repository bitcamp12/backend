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
            SELECT * FROM qna
            WHERE play_seq = #{playSeq}
            ORDER BY created_date DESC
            """)
    List<QnaDTO> getQnaList(int playSeq);

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

	
}
