package com.example.demo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.example.demo.dto.BookDTO;
import com.example.demo.dto.member.MemberDTO;

@Mapper
public interface BookDAO {

    @Select("""
            SELECT 
            ptt.theater_seq,
            b.play_time_table_seq,
            b.book_seq,
            b.payment_status,
            b.booked_x,
            b.booked_y
            FROM 
                book b
            INNER JOIN 
                play_time_table ptt 
                ON b.play_time_table_seq = ptt.play_time_table_seq
            WHERE 
                b.play_time_table_seq = #{playTimeTableSeq}
                AND payment_status = 'PAID'
            """)
    List<BookDTO> getBookedSeats(int playTimeTableSeq);

    @Insert("""
            INSERT INTO book (member_seq, play_time_table_seq, booked_x, booked_y, payment, total_price, payment_status)
            VALUES (#{memberSeq}, #{playTimeTableSeq}, #{bookedX}, #{bookedY}, 'Credit Card', #{totalPrice}, 'PAID')
            """)
    void insertSeat(BookDTO seat);

	
}
