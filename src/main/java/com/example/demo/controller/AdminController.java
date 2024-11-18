package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

// Admin = 사이트 관리자
@Controller
@RequestMapping(value="admin")
public class AdminController {
    
    @GetMapping(value = {"/", ""})
	public String index(Model model) {
        return "admin/index";  // index.html 템플릿을 렌더링
    }
}
