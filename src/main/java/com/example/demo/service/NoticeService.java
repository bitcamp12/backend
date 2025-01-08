package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.NoticeDAO;
import com.example.demo.dto.NoticeDTO;

@Service
public class NoticeService {

	@Autowired
	private NoticeDAO noticeDAO;

	public List<NoticeDTO> getNoticeAll() {
		return noticeDAO.getNoticeAll();
	}
}
