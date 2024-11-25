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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.member.IdCheckDTO;
import com.example.demo.dto.member.IdFindDTO;
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
    
    //회원가입 이메일
    @GetMapping("/sendNumber")
    public ResponseEntity<ApiResponse<String>> sendNumber(@RequestParam("email") String email) {
        try {
        	System.out.println(email);
            // 랜덤 번호 생성 및 이메일로 전송
            String verificationCode = emailService.sendRandomNumberEmail(email);
            System.out.println(verificationCode);
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
    //회원가입 이메일코드확인
    @GetMapping("/verifyCode")
    public ResponseEntity<ApiResponse<String>> verifyCode(@RequestParam("email") String email, @RequestParam("code") String code) {
        // 만료 시간 확인
    	System.out.println(email+code);
        Long expirationTime = codeExpirationTimes.get(email);
        if (expirationTime != null && System.currentTimeMillis() > expirationTime) {
            // 만료된 경우
            emailVerificationCodes.remove(email);
            codeExpirationTimes.remove(email);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(400, "expired", null));
        }

        // 임시 저장된 번호와 비교
        String storedCode = emailVerificationCodes.get(email);
        System.out.println(storedCode);
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
    
    
    //아이디찾기 이메일
    @GetMapping("/sendEmailVerificationCode")
    public ResponseEntity<ApiResponse<String>> sendNumber(@RequestParam("email") String email, @RequestParam("name") String name) {
        try {
        	System.out.println(email+name);
            Map<String, String> map = new HashMap<>();
            map.put("name", name);
            map.put("email", email);
            
        	int result = memberService.findIdByEmail(map);
        	System.out.println("result"+result);
        	if (result == 0) {
                return ResponseEntity.status(HttpStatus.OK)
                        .body(new ApiResponse<>(400, "nomember", null));
        	}
        	
        	System.out.println(email);
            // 랜덤 번호 생성 및 이메일로 전송
            String verificationCode = emailService.sendRandomNumberEmail(email);
            System.out.println(verificationCode);
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
    
    
    //아이디찾기 이메일코드확인
    @GetMapping("/verifyCodeId")
    public ResponseEntity<ApiResponse<String>> verifyCode(@RequestParam("email") String email, @RequestParam("code") String code, @RequestParam("name") String name) {
        // 만료 시간 확인
    	System.out.println(email+code+name);
        Long expirationTime = codeExpirationTimes.get(email);
        if (expirationTime != null && System.currentTimeMillis() > expirationTime) {
            // 만료된 경우
            emailVerificationCodes.remove(email);
            codeExpirationTimes.remove(email);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>(400, "expired", null));
        }

        // 임시 저장된 번호와 비교
        String storedCode = emailVerificationCodes.get(email);
        System.out.println(storedCode);
        if (storedCode != null && storedCode.equals(code)) {
        	
            Map<String, String> map = new HashMap<>();
            map.put("name", name);
            map.put("email", email);
            // 인증 성공 시 코드 제거
            emailVerificationCodes.remove(email);
            codeExpirationTimes.remove(email);
             
            String id = memberService.findIdByEmail2(map);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>(200, id, null));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(400, "not_match", null));
        }
    }
    
    //회원가입 SMS
    @PostMapping("/sendPhoneNumber")
    public ResponseEntity<ApiResponse<String>> sendPhoneNumber(@RequestBody SmsRequestDto smsRequestDto) {
        try {
            // 인증 코드 생성 및 SMS 전송
//            String certificationCode = smsService.sendSms(smsRequestDto);
        	 String certificationCode = "111";

            // 전화번호와 인증 코드 저장
            String phoneNum = smsRequestDto.getPhoneNum();
            emailVerificationCodes.put(phoneNum, certificationCode);
            long expirationTime = System.currentTimeMillis() + EXPIRATION_TIME;
            codeExpirationTimes.put(phoneNum, expirationTime);

            // 만료 시간 이후 삭제 처리
            scheduler.schedule(() -> {
                if (System.currentTimeMillis() >= expirationTime) {
                    emailVerificationCodes.remove(phoneNum);
                    codeExpirationTimes.remove(phoneNum);
                }
            }, EXPIRATION_TIME, TimeUnit.MILLISECONDS);

            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>(200, "인증번호 발송 완료", null));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(500, "SMS 발송 실패", null));
        }
    }
    
    //아이디찾기 SMS
    @PostMapping("/sendPhoneVerificationCode")
    public ResponseEntity<ApiResponse<String>> sendPhoneVerificationCode(@RequestBody SmsRequestDto smsRequestDto) {
        try {
            // 인증 코드 생성 및 SMS 전송
            //String certificationCode = smsService.sendSms(smsRequestDto);
        	 String certificationCode = "111";

            // 전화번호와 인증 코드 저장
            String phoneNum = smsRequestDto.getPhoneNum();
            String name =smsRequestDto.getName();
            
            Map<String, String> map = new HashMap<>();
            map.put("name", name);
            map.put("phone", phoneNum);
            
        	int result = memberService.findIdByPhone(map);
        	System.out.println("result"+result);
        	if (result == 0) {
                return ResponseEntity.status(HttpStatus.OK)
                        .body(new ApiResponse<>(400, "nomember", null));
        	}
            
            
            
            emailVerificationCodes.put(phoneNum, certificationCode);
            long expirationTime = System.currentTimeMillis() + EXPIRATION_TIME;
            codeExpirationTimes.put(phoneNum, expirationTime);

            // 만료 시간 이후 삭제 처리
            scheduler.schedule(() -> {
                if (System.currentTimeMillis() >= expirationTime) {
                    emailVerificationCodes.remove(phoneNum);
                    codeExpirationTimes.remove(phoneNum);
                }
            }, EXPIRATION_TIME, TimeUnit.MILLISECONDS);

            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>(200, "인증번호 발송 완료", null));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(500, "SMS 발송 실패", null));
        }
    }
    
    @GetMapping("/verifyPhone")
    public ResponseEntity<ApiResponse<String>> verifyPhoneNumberCode(@RequestParam("phoneNum") String phoneNum, @RequestParam("code") String code) {
        Long expirationTime = codeExpirationTimes.get(phoneNum);
        System.out.println(phoneNum+code);
        if (expirationTime != null && System.currentTimeMillis() > expirationTime) {
            // 만료된 경우
            emailVerificationCodes.remove(phoneNum);
            codeExpirationTimes.remove(phoneNum);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(400, "expired", null));
        }

        String storedCode = emailVerificationCodes.get(phoneNum);
        System.out.println(storedCode);
        if (storedCode != null && storedCode.equals(code)) {
            // 인증 성공 시 코드 제거
            emailVerificationCodes.remove(phoneNum);
            codeExpirationTimes.remove(phoneNum);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>(200, "match", null));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(400, "not_match", null));
        }
    }
    
    @PostMapping("/getIdByPhone")
    public ResponseEntity<ApiResponse<String>> checkId(@RequestBody IdFindDTO dto) {
        try {
            System.out.println("Received data: " + dto.getName() + " " + dto.getPhone());

            String name = dto.getName();
            String phone = dto.getPhone();
            
            Map<String, String> map = new HashMap<>();
            map.put("name", name);
            map.put("phone", phone);

            String result = memberService.findIdPhone(map);
            System.out.println(result);
            if (result != null) {
                return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(200, result, null));
            } else {
                return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(404, null, "회원정보없음"));
            }
        } catch (Exception e) {
            System.err.println("Error occurred: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body(new ApiResponse<>(500, null, "에러"));
        }
    }

    



    


    



	@PostMapping("login")
	public String login(Model model) {
        return "login";  
    }
	

	
// -- 지현 : 마이페이지(사용자정보) 수정 ---------------------------
	
	// 한 명의 사용자 정보를 가져옵니다.
	@GetMapping("getUserInfo/{id}")
	public MemberDTO getUserInfo(@PathVariable("id") String id) {
		MemberDTO memberDTO = memberService.getUserInfo(id);
		return memberDTO;
	}
	

}
