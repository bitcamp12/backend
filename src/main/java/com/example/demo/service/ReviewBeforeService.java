package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.ReviewBeforeDAO;
import com.example.demo.dto.ReviewAfterDTO;
import com.example.demo.dto.ReviewBeforeDTO;

@Service
public class ReviewBeforeService {

	@Autowired
	private ReviewBeforeDAO reviewBeforeDAO;

	public int reviewBWrite(int playSeq, int memberSeq, String content) {
		
		return reviewBeforeDAO.reviewBWrite(playSeq,memberSeq,content);
	}

	public List<ReviewBeforeDTO> getReviewBList(int playSeq) {
		// TODO Auto-generated method stub
		return reviewBeforeDAO.getReviewBList(playSeq);
	}

	public ReviewBeforeDTO getReviewB(int reviewBeforeSeq) {
		// TODO Auto-generated method stub
		return reviewBeforeDAO.getReviewB(reviewBeforeSeq);
	}
	public int reviewBUpdate(int reviewBeforeSeq, String content) {
		// TODO Auto-generated method stub
		return reviewBeforeDAO.reviewBUpdate(reviewBeforeSeq,content);
	}

	public int reviewBDelete(int reviewBeforeSeq) {
		// TODO Auto-generated method stub
		return reviewBeforeDAO.reviewBDelete(reviewBeforeSeq);
	}

	public int ReviewBcount(int playSeq) {
		// TODO Auto-generated method stub
		return reviewBeforeDAO.ReviewBcount(playSeq);
	}

	
}
