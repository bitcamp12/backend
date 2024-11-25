package com.example.demo.dao;

import java.util.Map;

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
    
    @Select("SELECT id FROM member WHERE name = #{name} AND phone = #{phone}")
	public String findIdPhone(Map<String, String> map);
    
    @Select("SELECT COUNT(*) FROM member WHERE name = #{name} AND email = #{email}")
    public int findIdByEmail(Map<String, String> map);
    
    @Select("SELECT id FROM member WHERE name = #{name} AND email = #{email}")
	public String findIdByEmail2(Map<String, String> map);

    @Select("SELECT * FROM member WHERE id= #{id}")
	public MemberDTO getUserInfo(String id);
    
    @Select("SELECT COUNT(*) FROM member WHERE name = #{name} AND phone = #{phone}")
	public int findIdByPhone(Map<String, String> map);
    
    
    

}
