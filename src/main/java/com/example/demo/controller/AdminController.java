package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

// Admin = 사이트 관리자
@Controller
@RequestMapping(value="/secure/admin")
public class AdminController {
    
    @GetMapping("/index")
	public String index(Model model) {
    	//관리자 정보 추후 추가하기 model 어트리뷰트..//
    	
        return "admin/index";  // index.html 템플릿을 렌더링
    }
}
