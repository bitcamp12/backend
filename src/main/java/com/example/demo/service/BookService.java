package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.BookDAO;
import com.example.demo.dto.BookDTO;

@Service
public class BookService {

	@Autowired
	private BookDAO bookDAO;

    public List<BookDTO> getBookedSeats(int playTimeTableSeq) {
        return bookDAO.getBookedSeats(playTimeTableSeq);
    }

    public void purchaseSeats(List<BookDTO> list) {
        for (BookDTO seat : list) {
            bookDAO.insertSeat(seat);
        }
    }
}
