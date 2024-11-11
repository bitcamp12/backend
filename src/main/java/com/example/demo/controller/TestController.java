package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

// 관리자 페이지 띄우는 Controller
@Controller
public class TestController {

    // 타임리프 테스트 페이지(/test)
    @GetMapping("/test")
    public String test(Model model) {
        model.addAttribute("message", "Hello, Thymeleaf!");  // 모델에 데이터를 추가
        return "test";  // test.html 템플릿을 렌더링
    }
}
