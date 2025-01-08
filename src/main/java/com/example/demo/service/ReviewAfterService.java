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

	public List<ReviewAfterDTO> getReviewAList(int playSeq, int page, int size) {
		  int pages = (page > 0) ? (page - 1) * size : 0; // page가 0일 때 0으로 처리
		System.out.println(pages);
		return reviewAfterDAO.getReviewAList(playSeq,pages,size);
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

	public List<ReviewAfterDTO> getReviewAListStar(int playSeq, int page, int size) {
		  int pages = (page > 0) ? (page - 1) * size : 0; // page가 0일 때 0으로 처리
		
		return reviewAfterDAO.getReviewAListStar(playSeq,pages,size);
	}

	

	
	public List<ReviewAfterDTO> ReviewASearchIdDate(String keyword, int playSeq, int page, int size) {
		int pages = (page - 1) * size ; // page가 0일 때 0으로 처리
		return reviewAfterDAO.ReviewASearchIdDate(keyword,playSeq,pages,size);
	}

	public List<ReviewAfterDTO> ReviewASearchIdRating(String keyword, int playSeq, int page, int size) {
		int pages = (page - 1) * size ; // page가 0일 때 0으로 처리
		return reviewAfterDAO.ReviewASearchIdRating(keyword,playSeq,pages,size);
	}

	public List<ReviewAfterDTO> ReviewASearchDate(String keyword, int playSeq, int page, int size) {
		int pages =  (page - 1) * size ; // page가 0일 때 0으로 처리
	
		return reviewAfterDAO.ReviewASearchDate(keyword,playSeq,pages,size);
	}

	public List<ReviewAfterDTO> ReviewASearchRating(String keyword, int playSeq, int page, int size) {
		int pages =  (page - 1) * size ; // page가 0일 때 0으로 처리
		return reviewAfterDAO.ReviewASearchRating(keyword,playSeq,pages,size);
	}

	
	
	
	
	
	
	public int ReviewASearchIdDateCount(String keyword, int playSeq) {
		// TODO Auto-generated method stub
		return reviewAfterDAO.ReviewASearchIdDateCount(keyword,playSeq);
	}

	public int ReviewASearchIdRatingCount(String keyword, int playSeq) {
		// TODO Auto-generated method stub
		return reviewAfterDAO.ReviewASearchIdRatingCount(keyword,playSeq);
	}

	public int ReviewASearchDateCount(String keyword, int playSeq) {
		// TODO Auto-generated method stub
		return reviewAfterDAO.ReviewASearchDateCount(keyword,playSeq);
	}

	public int ReviewASearchRatingCount(String keyword, int playSeq) {
		// TODO Auto-generated method stub
		return reviewAfterDAO.ReviewASearchRatingCount(keyword,playSeq);
	}

	
	
}
