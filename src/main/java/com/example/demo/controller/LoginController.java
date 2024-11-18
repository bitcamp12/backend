package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

	@GetMapping("admin/login")
	public String login(Model model) {
        return "login";  // login.html 템플릿을 렌더링
    }

}
