// package com.example.demo.controller;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;

// import com.example.demo.service.TestService;
// import com.example.demo.util.ApiResponse;

// @RestController
// @RequestMapping(value = "/test")
// public class TestController {

//     @Autowired
//     private TestService TestService;

//     @PostMapping(value = "seats")
// 	public ResponseEntity<ApiResponse<Void>> test() {
// 		try {
// 			TestService.test();
// 			return ResponseEntity.ok(new ApiResponse<>(200, "성공", null));
// 		} catch (Exception e) {
// 			e.printStackTrace();
// 			return ResponseEntity.ok(new ApiResponse<>(500, "서버 오류", null));
// 		}
// 	}
// }
