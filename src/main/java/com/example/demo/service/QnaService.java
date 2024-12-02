package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.QnaDAO;
import com.example.demo.dto.QnaDTO;

@Service
public class QnaService {

	@Autowired
	private QnaDAO qnaDAO;

	public int qnaWrite(int playSeq, int memberSeq, String title, String content) {
		// TODO Auto-generated method stub
		return qnaDAO.qnaWrite(playSeq,memberSeq,title,content);
	}

	

	public List<QnaDTO> getQnaList(int playSeq, int page, int size) {
		int pages=(page-1)*size;
		// TODO Auto-generated method stub
		return qnaDAO.getQnaList(playSeq,pages,size);
	}



	public QnaDTO getQnaOne(int qnaSeq) {
		// TODO Auto-generated method stub
		return qnaDAO.getQnaOne(qnaSeq);
	}



	public int updateQna(int qnaSeq, String title, String content) {
		// TODO Auto-generated method stub
		return qnaDAO.updateQna(qnaSeq,title,content);
	}



	public int deleteQna(int qnaSeq) {
		// TODO Auto-generated method stub
		return qnaDAO.deleteQna(qnaSeq);
	}



	public int qnaCount(int playSeq) {
		// TODO Auto-generated method stub
		return qnaDAO.qnaCount(playSeq);
	}



	public List<QnaDTO> qnaSearchId(String keyword, int playSeq) {
		// TODO Auto-generated method stub
		return qnaDAO.qnaSearchId(keyword,playSeq);
	}



	public List<QnaDTO> qnaSearchKey(String keyword, int playSeq) {
		// TODO Auto-generated method stub
		return qnaDAO.qnaSearchKey(keyword,playSeq);
	}
}
