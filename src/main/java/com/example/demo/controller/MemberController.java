package com.example.demo.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.member.IdCheckDTO;
import com.example.demo.dto.member.MemberDTO;
import com.example.demo.dto.member.SmsRequestDto;
import com.example.demo.entity.Member;
import com.example.demo.service.EmailService;
import com.example.demo.service.MemberService;
import com.example.demo.service.SmsService;
import com.example.demo.util.ApiResponse;

@RestController
@RequestMapping("api/members")
public class MemberController {
	
	@Autowired
	MemberService memberService;
	
    @Autowired
    private EmailService emailService;
    
    @Autowired
    private SmsService smsService;

    // 임시로 랜덤 번호를 저장할 Map
    private Map<String, String> emailVerificationCodes = new HashMap<>();
    
    private Map<String, Long> codeExpirationTimes = new HashMap<>();
    
    // 제한 시간 설정 (밀리초 단위, 예: 2분 = 2 * 60 * 1000)
    private static final long EXPIRATION_TIME = 1 * 60 * 1000;

    // 스케줄러를 사용하여 만료 시간 이후 데이터를 제거
    private ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
	
    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<Member>> signUp(@RequestBody MemberDTO memberDTO) {
        System.out.println("Received data: " + memberDTO);
        try {
        	int result = memberService.signUp(memberDTO);
	        if(result == 1) {
	        	return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(200, "회원가입을 축하드립니다.", null));
	        }
	    }catch(Exception e) {
	    	e.printStackTrace();
        	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(400, "회원가입을 실패했습니다.", null));
        }
        
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(200, "success", null));
    }
    
    @PostMapping("/checkId")
    public ResponseEntity<ApiResponse<Member>> checkId(@RequestBody IdCheckDTO dto) {
        System.out.println("Received data: " + dto.getId());
        try {
            int result = memberService.checkId(dto.getId());
            System.out.println(result);
            if (result == 1) {
                return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(200, "exist", null));
            } else if (result == 0) {
                return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(200, "non_exist", null));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(400, "오류", null));
        }

        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(200, "non_exist", null));
    }
    
    @GetMapping("/sendNumber")
    public ResponseEntity<ApiResponse<String>> sendNumber(@RequestParam("email") String email) {
        try {
            // 랜덤 번호 생성 및 이메일로 전송
            String verificationCode = emailService.sendRandomNumberEmail(email);

            // 이메일과 랜덤 번호 및 만료 시간 저장
            emailVerificationCodes.put(email, verificationCode);
            long expirationTime = System.currentTimeMillis() + EXPIRATION_TIME;
            codeExpirationTimes.put(email, expirationTime);

            // 만료 시간 이후 인증번호 제거
            scheduler.schedule(() -> {
                if (System.currentTimeMillis() >= expirationTime) {
                    emailVerificationCodes.remove(email);
                    codeExpirationTimes.remove(email);
                }
            }, EXPIRATION_TIME, TimeUnit.MILLISECONDS);

            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>(200, "인증번호 발송 완료", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(500, "이메일 발송 실패", null));
        }
    }
    
    @GetMapping("/verifyCode")
    public ResponseEntity<ApiResponse<String>> verifyCode(@RequestParam("email") String email, @RequestParam("code") String code) {
        // 만료 시간 확인
        Long expirationTime = codeExpirationTimes.get(email);
        System.out.println(expirationTime);
        if (expirationTime != null && System.currentTimeMillis() > expirationTime) {
            // 만료된 경우
            emailVerificationCodes.remove(email);
            codeExpirationTimes.remove(email);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(400, "expired", null));
        }

        // 임시 저장된 번호와 비교
        String storedCode = emailVerificationCodes.get(email);
        if (storedCode != null && storedCode.equals(code)) {
            // 인증 성공 시 코드 제거
            emailVerificationCodes.remove(email);
            codeExpirationTimes.remove(email);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>(200, "match", null));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(400, "not_match", null));
        }
    }
    
    @PostMapping("/sendPhoneNumber")
    public ResponseEntity<?> SendSMS(@RequestBody SmsRequestDto smsRequestDto){
    	System.out.println(smsRequestDto.getPhoneNum());
        smsService.sendSms(smsRequestDto);
        return ResponseEntity.ok("문자를 전송했습니다.");
    }
    



	@PostMapping("login")
	public String login(Model model) {
        return "login";  
    }
	

}
