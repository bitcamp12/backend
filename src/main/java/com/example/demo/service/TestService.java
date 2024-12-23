package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dao.TestDAO;
import com.example.demo.dto.BookDTO;

@Service
public class TestService {

    @Autowired
    private TestDAO testDAO;

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void test() {
        int playTimeTableSeq = 101;
        int bookedX = 5;
        int bookedY = 7;
        List<BookDTO> bookDTO = testDAO.test(playTimeTableSeq, bookedX, bookedY);
        if (bookDTO.size() > 0) {
            throw new RuntimeException("이미 예약된 좌석입니다.");
        }
        bookDTO.get(0).setMemberSeq(1);
        testDAO.insertSeat(bookDTO.get(0));
    }

}
