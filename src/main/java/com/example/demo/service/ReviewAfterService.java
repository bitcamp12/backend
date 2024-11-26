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

	public int reviewAWrite(int playSeq, int memberSeq, String content, int rating) {
		System.out.println(playSeq+rating+memberSeq+content);
		 int result=reviewAfterDAO.reviewAWrite(playSeq,memberSeq,content,rating);
		return result;
	}

	public List<ReviewAfterDTO> getReviewAList(int playSeq) {
		
		return reviewAfterDAO.getReviewAList(playSeq);
	}

	public ReviewAfterDTO getReviewA(int reviewAfterSeq) {
		
		return reviewAfterDAO.getReviewA(reviewAfterSeq);
	}

	public int reviewAUpdate(int  reviewAfterSeq, String content, int rating) {
		
		System.out.println(content+rating+reviewAfterSeq);
		int result =reviewAfterDAO.reviewAUpdate(reviewAfterSeq,content,rating);
		System.out.println(result);
		return result;
	}

	public int reviewADelete(int  reviewAfterSeq) {
		// TODO Auto-generated method stub
		int result= reviewAfterDAO.reviewADelete(reviewAfterSeq);
		
		return result;
	}

	public int reviewACount(int playSeq) {
		// TODO Auto-generated method stub
		return reviewAfterDAO.reviewACount(playSeq);
	}

	public Float ReviewAAvg(int playSeq) {
		// TODO Auto-generated method stub
		return reviewAfterDAO.ReviewAAvg(playSeq);
	}

	public List<ReviewAfterDTO> getReviewAListStar(int playSeq) {
		// TODO Auto-generated method stub
		return reviewAfterDAO.getReviewAListStar(playSeq);
	}

	public List<ReviewAfterDTO> ReviewASearch(String keyword) {
		// TODO Auto-generated method stub
		return reviewAfterDAO.ReviewASearch(keyword);
	}

	public List<ReviewAfterDTO> ReviewASearchId(String keyword) {
		// TODO Auto-generated method stub
		return reviewAfterDAO.ReviewASearchId(keyword);
	}

	public List<ReviewAfterDTO> ReviewASearchIdDate(String keyword) {
		// TODO Auto-generated method stub
		return reviewAfterDAO.ReviewASearchIdDate(keyword);
	}

	public List<ReviewAfterDTO> ReviewASearchIdRating(String keyword) {
		// TODO Auto-generated method stub
		return reviewAfterDAO.ReviewASearchIdRating(keyword);
	}

	public List<ReviewAfterDTO> ReviewASearchDate(String keyword) {
		// TODO Auto-generated method stub
		return reviewAfterDAO.ReviewASearchDate(keyword);
	}

	public List<ReviewAfterDTO> ReviewASearchRating(String keyword) {
		// TODO Auto-generated method stub
		return reviewAfterDAO.ReviewASearchRating(keyword);
	}

	
	
}
