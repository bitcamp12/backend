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
    @GetMapping("/playRegisterForm")
	public String playReg(Model model) {
        return "/seller/body/playRegisterForm";  
    }
    
    // 공연정보수정
    @GetMapping("/playUpdate")
    public String playUpdate(Model model) {
    	return "/seller/body/playUpdate";  
    }
    
    // 공연정보리스트
    @GetMapping("/playList")
    public String playList(Model model) {
    	return "/seller/body/playList";  
    }
    
    // 예매정보관리
    @GetMapping("/bookList")
    public String bookList(Model model) {
    	return "/seller/body/bookList";  
    }
    
    // QnA 게시판
    @GetMapping("/qnaList")
    public String qnaList(Model model) {
    	return "/seller/body/qnaList";  
    }
    


    
}

/*
    // 공연정보등록
    @GetMapping("/playRegisterForm")
	public String playReg(Model model) {
        return "seller/body/playRegisterForm";  
    }

    // 공연정보수정
    @GetMapping("/playUpdate")
    public String playUdt(Model model) {
    	return "seller/body/playUpdate";  
    }
    
    // 공연정보리스트
    @GetMapping("/playList")
    public String playList(Model model) {
    	return "seller/body/playList";  
    }
    
    // 예매관리
    @GetMapping("/bookList")
    public String bookList(Model model) {
    	return "seller/body/bookList";  
    }
 */