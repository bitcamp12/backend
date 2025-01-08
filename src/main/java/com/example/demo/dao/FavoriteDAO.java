package com.example.demo.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.example.demo.dto.member.MemberDTO;

@Mapper
public interface FavoriteDAO {

	
	 // 즐겨찾기 추가
    @Insert("""
            INSERT INTO favorite (play_seq, member_seq)
            VALUES (#{playSeq}, #{memberSeq})
            """)
    int favoritesInsert(@Param("playSeq") int playSeq, @Param("memberSeq") int memberSeq);

    // 즐겨찾기 여부 확인
    @Select("""
            SELECT COUNT(*) 
            FROM favorite 
            WHERE play_seq = #{playSeq} AND member_seq = #{memberSeq}
            """)
    int favoritesCheck(@Param("playSeq") int playSeq, @Param("memberSeq") int memberSeq);

    // 즐겨찾기 삭제
    @Delete("""
            DELETE FROM favorite
            WHERE play_seq = #{playSeq} AND member_seq = #{memberSeq}
            """)
    int favoritesDelete(@Param("playSeq") int playSeq, @Param("memberSeq") int memberSeq);

	
}
