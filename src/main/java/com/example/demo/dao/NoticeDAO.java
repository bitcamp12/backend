package com.example.demo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.example.demo.dto.NoticeDTO;

@Mapper
public interface NoticeDAO {

    @Select("""
            SELECT * FROM notice
            """)
    List<NoticeDTO> getNoticeAll();

	
}
