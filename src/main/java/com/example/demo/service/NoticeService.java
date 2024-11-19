package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.NoticeDAO;

@Service
public class NoticeService {

	@Autowired
	private NoticeDAO noticeDAO;
}
