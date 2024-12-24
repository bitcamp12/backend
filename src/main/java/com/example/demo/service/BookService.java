package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;

import com.example.demo.aop.TimeTrace;
import com.example.demo.dao.BookDAO;
import com.example.demo.dto.BookDTO;

import org.springframework.transaction.annotation.Transactional;

@Service
public class BookService {

	@Autowired
	private BookDAO bookDAO;

    public List<BookDTO> getBookedSeats(int playTimeTableSeq) {
        return bookDAO.getBookedSeats(playTimeTableSeq);
    }

    @TimeTrace
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void purchaseSeats(List<BookDTO> list) {
        List<BookDTO> bookedSeats = bookDAO.getBookedSeats(list.get(0).getPlayTimeTableSeq());
        
        for (BookDTO seat : list) {
            boolean isBooked = bookedSeats.stream()
                                          .anyMatch(bookedSeat -> bookedSeat.getBookedX() == seat.getBookedX() && bookedSeat.getBookedY() == seat.getBookedY());
            if (isBooked) {
                throw new IllegalStateException("이미 예약된 좌석입니다.");
            }
        }
        for (BookDTO seat : list) {
            bookDAO.insertSeat(seat);
        }
    }
    
}
