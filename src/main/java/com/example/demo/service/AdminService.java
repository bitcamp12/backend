package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.AdminDAO;
import com.example.demo.dao.MemberDAO;
import com.example.demo.dto.member.MemberDTO;

@Service
public class AdminService {

	@Autowired
	private AdminDAO adminDAO;
	

}
