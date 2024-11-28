package com.example.demo.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.demo.dto.member.MemberDTO;

@Mapper
public interface MemberDAO {
    
    @Insert("INSERT INTO member (id, name, password, email, phone, address, gender, sns_token, role) " +
            "VALUES (#{id}, #{name}, #{password}, #{email}, #{phone}, #{address}, #{gender}, #{snsToken}, #{role})")
    public int signUp(MemberDTO memberDTO);
    
    @Select("""
    	    SELECT COUNT(*)
    	    FROM (
    	        SELECT id FROM member WHERE id = #{id}
    	        UNION
    	        SELECT id FROM admin WHERE id = #{id}
    		    ) AS combined
    		""")
    	public int checkId(String id);
    
    @Select("SELECT id FROM member WHERE name = #{name} AND phone = #{phone}")
	public String findIdPhone(Map<String, String> map);
    
    @Select("SELECT COUNT(*) FROM member WHERE name = #{name} AND email = #{email}")
    public int findIdByEmail(Map<String, String> map);
    
    @Select("SELECT id FROM member WHERE name = #{name} AND email = #{email}")
	public String findIdByEmail2(Map<String, String> map);

    @Select("SELECT * FROM member WHERE id= #{id}")
	public MemberDTO getUserInfo(String id);

    @Update("UPDATE member SET phone=#{phone}, email=#{email} WHERE id=#{id}")
    public void modifyUserInfo(MemberDTO modifiedData);
    
    @Select("SELECT COUNT(*) FROM member WHERE name = #{name} AND phone = #{phone}")
	public int findIdByPhone(Map<String, String> map);

    @Update("UPDATE member SET password = #{password} WHERE id = #{id}")
    public int updatePwd(Map<String, String> map);
    
    @Select("SELECT COUNT(*) FROM member WHERE id = #{id} AND password = #{password}")
    public int Login(Map<String, String> map);


    @Delete("DELETE FROM member WHERE id=#{id}")
	public void infoWithdrawal(String id);

    
    @Select("SELECT member_seq FROM member WHERE id = #{id}")
	public int getMemberSeq(String userId);

    @Select("SELECT COUNT(*) FROM member WHERE name = #{name} AND phone = #{phone} AND id =#{id}")
	public int findPwdByPhone(Map<String, String> map);
    
    @Select("SELECT COUNT(*) FROM member WHERE name = #{name} AND email = #{email} AND id =#{id}")
	public int findPwdByEmail(Map<String, String> map);


}
