package com.example.demo.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Mapper;
import com.example.demo.dto.member.MemberDTO;

@Mapper
public interface MemberDAO {
    
    @Insert("INSERT INTO member (id, name, password, email, phone, address, gender, sns_token, role) " +
            "VALUES (#{id}, #{name}, #{password}, #{email}, #{phone}, #{address}, #{gender}, #{snsToken}, #{role})")
    public int signUp(MemberDTO memberDTO);

    @Select("SELECT COALESCE(COUNT(*), 0) FROM member WHERE id = #{id}")
    public int checkId(String id); 
}
