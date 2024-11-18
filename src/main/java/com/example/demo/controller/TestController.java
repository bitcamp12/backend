package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.entity.TestEntity;
import com.example.demo.service.TestService;

// 관리자 페이지 띄우는 Controller
@Controller
public class TestController {

	@Autowired
	TestService testService;
	
    // 타임리프 테스트 페이지(/test)
    @GetMapping("/test")
    public String test(Model model) {
    	TestEntity testEntity = testService.test();
        model.addAttribute("message", testEntity.getMessage());  // 모델에 데이터를 추가
        return "test";  // test.html 템플릿을 렌더링
    }
    
    @GetMapping("/notice")
    public String notice(Model model) {
    	return "admin/body/memberList";  // test.html 템플릿을 렌더링
    }
}
