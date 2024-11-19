package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/secure")
@Controller
public class LoginController {

	@GetMapping("/login")
	public String login(Model model) {
        return "login";  // login.html 템플릿을 렌더링
    }

}
