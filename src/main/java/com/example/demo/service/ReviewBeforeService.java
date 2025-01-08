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

	public List<ReviewBeforeDTO> getReviewBList(int playSeq, int page, int size) {
		System.out.println(page);
		int pages = (page - 1) * size ; // page가 0일 때 0으로 처리
		System.out.println(pages);
		 
		return reviewBeforeDAO.getReviewBList(playSeq,pages,size);
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

	public List<ReviewBeforeDTO> ReviewBSearchId(String keyword, int playSeq, int page, int size) {
		int pages=(page-1)*size;
		return reviewBeforeDAO.ReviewBSearchId(keyword,playSeq,pages,size);
	}

	public List<ReviewBeforeDTO> ReviewBSearchKey(String keyword, int playSeq, int page, int size) {
		int pages=(page-1)*size;
		return reviewBeforeDAO.ReviewBSearchKey(keyword,playSeq,pages,size);
	}

	public int ReviewBSearchIdCount(String keyword, int playSeq) {
		// TODO Auto-generated method stub
		return reviewBeforeDAO.ReviewBSearchIdCount(keyword,playSeq);
	}

	public int ReviewBSearchKeyCount(String keyword, int playSeq) {
		// TODO Auto-generated method stub
		return reviewBeforeDAO.ReviewBSearchKeyCount(keyword,playSeq);
	}

	
}
