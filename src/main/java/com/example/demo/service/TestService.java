package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.TestDAO;
import com.example.demo.entity.TestEntity;

@Service
public class TestService {

	@Autowired 
	TestDAO testDAO;
	
	public TestEntity test() {
		TestEntity testEntity = new TestEntity();
		testEntity.setMessage(testDAO.test());
		return testEntity;
	}
}
