package com.example.demo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.example.demo.dto.BookDTO;

@Mapper
public interface TestDAO {
    
    @Select("""
            SELECT 
            b.play_time_table_seq,
            b.book_seq,
            b.payment_status,
            b.booked_x,
            b.booked_y
            FROM 
                book b
            WHERE 
                b.play_time_table_seq = #{playTimeTableSeq}
                AND payment_status = 'PAID'
                AND b.booked_x = #{bookedX}
                AND b.booked_y = #{bookedY}
            FOR UPDATE
            """)
    public List<BookDTO> test(int playTimeTableSeq, int bookedX, int bookedY);

    @Insert("""
            INSERT INTO book (member_seq, play_time_table_seq, booked_x, booked_y, payment, total_price, payment_status)
            VALUES (#{memberSeq}, #{playTimeTableSeq}, #{bookedX}, #{bookedY}, 'Credit Card', #{totalPrice}, 'PENDING')
            """)
    void insertSeat(BookDTO seat);
}
