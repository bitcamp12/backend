package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.ReviewAfterDAO;
import com.example.demo.dto.ReviewAfterDTO;

@Service
public class ReviewAfterService {

	@Autowired
	private ReviewAfterDAO reviewAfterDAO;

	public void reviewAWrite(int playSeq, int memberSeq, String content, String rating) {
		
		 reviewAfterDAO.reviewAWrite(playSeq,memberSeq,content,rating);
	}

	public List<ReviewAfterDTO> getReviewAList(int playSeq) {
		
		return reviewAfterDAO.getReviewAList(playSeq);
	}

	public ReviewAfterDTO getReviewA(String reviewAfterSeq) {
		
		return reviewAfterDAO.getReviewA(reviewAfterSeq);
	}

	public void reviewAUpdate(String reviewAfterSeq, String content, String rating) {
		
		reviewAfterDAO.reviewAUpdate(reviewAfterSeq,content,rating);
	}

	public void reviewADelete(String reviewAfterSeq) {
		// TODO Auto-generated method stub
		reviewAfterDAO.reviewADelete(reviewAfterSeq);
	}

	
	
}
