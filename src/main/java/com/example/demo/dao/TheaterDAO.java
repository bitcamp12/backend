package com.example.demo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.example.demo.dto.TheaterDTO;
import com.example.demo.dto.member.MemberDTO;

@Mapper
public interface TheaterDAO {

    @Select("""
            SELECT * FROM theater
            """)
    List<TheaterDTO> getTheaterInfo();

	
}
