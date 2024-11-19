package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

//Seller = 공연 관계자
@Controller
@RequestMapping(value="/secure/seller")
public class SellerController {

	@GetMapping("/index")
	public String index(Model model) {
        return "seller/index";  // index.html 템플릿을 렌더링
    }
    

    // 공연정보등록
    @GetMapping("/playReg")
	public String playReg(Model model) {
        return "seller/playReg";  
    }
    
    // 공연정보리스트
    @GetMapping("/playList")
    public String playList(Model model) {
    	return "seller/playList";  
    }

}
