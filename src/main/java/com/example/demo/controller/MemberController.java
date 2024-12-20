package com.example.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.CheckMyBookDTO;
import com.example.demo.dto.member.IdCheckDTO;
import com.example.demo.dto.member.IdFindDTO;
import com.example.demo.dto.member.JoinDTO;
import com.example.demo.dto.member.MemberDTO;
import com.example.demo.dto.member.SmsRequestDto;
import com.example.demo.entity.Book;
import com.example.demo.entity.Member;
import com.example.demo.entity.ReviewBefore;
import com.example.demo.service.CustomUserDetails;
import com.example.demo.service.EmailService;
import com.example.demo.service.MemberService;
import com.example.demo.service.SmsService;
import com.example.demo.util.ApiResponse;
import com.example.demo.util.AuthenticationFacade;
import com.example.demo.util.JWTUtil;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("api/members")
public class MemberController {
	
	@Autowired
	MemberService memberService;
	
    @Autowired
    private EmailService emailService;
    
    @Autowired
    private SmsService smsService;
    
    @Autowired
    private JWTUtil jwtUtil;
    
    @Autowired
    private AuthenticationFacade authenticationFacade;
    

    // 임시로 랜덤 번호를 저장할 Map
    private Map<String, String> emailVerificationCodes = new HashMap<>();
    
    private Map<String, Long> codeExpirationTimes = new HashMap<>();
    
    // 제한 시간 설정 (밀리초 단위, 예: 2분 = 2 * 60 * 1000)
    private static final long EXPIRATION_TIME = 2 * 60 * 1000;

    // 스케줄러를 사용하여 만료 시간 이후 데이터를 제거
    private ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
	
    /*
    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<Member>> signUp(@RequestBody MemberDTO memberDTO) {
    	String phone = memberDTO.getPhone().replaceAll("[^0-9]", "");
    	
    	String gender = memberDTO.getGender();
    	if(gender.equals("male")) {
    		memberDTO.setGender("M");
    	}
    	else {
    		memberDTO.setGender("F");
    	}
    	System.out.println(gender);
    	
    	memberDTO.setRole("USER");
    	memberDTO.setPhone(phone);
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
    */
    
    @GetMapping("/jwt")
    public String jwt() {
    	return "main Controller";
    }
    
    
    
    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<Member>> signUp(@RequestBody Member member) {
        try {
            // 전화번호 숫자만 남기기
            String phone = member.getPhone().replaceAll("[^0-9]", "");
            member.setPhone(phone);
            
            // 역할 기본값 설정
            member.setRole(Member.Role.USER);

            // 회원가입 처리
            int result = memberService.signUp(member);
            if (result == 1) {
                return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>(200, "회원가입을 축하드립니다.", null));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiResponse<>(400, "회원가입을 실패했습니다.", null));
        }

        return ResponseEntity.status(HttpStatus.CONFLICT)
            .body(new ApiResponse<>(409, "db에 중복된값이 존재합니다", null));
    }
    
    /*
    @PostMapping("/checkId")
    public ResponseEntity<ApiResponse<Member>> checkId(@RequestBody IdCheckDTO dto) {
        System.out.println("Received data: " + dto.getId());
        try {
            int result = memberService.checkId(dto.getId());   // 유니온 
            //int result2 = memberService.checkAdminId(dto.getId()); // 2번체크 
            System.out.println(result);
            if (result == 1 ) {
                return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(200, "exist", null));
            } else if (result == 0 ) {
                return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(200, "non_exist", null));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(400, "오류", null));
        }

        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(200, "non_exist", null));
    }
    */
    @PostMapping("/checkId")
    public ResponseEntity<ApiResponse<Member>> checkId(@RequestBody IdCheckDTO dto) {
        System.out.println("Received data: " + dto.getId());
        try {
            boolean isExist = memberService.checkIdEntity(dto.getId());   // 아이디 중복 체크 entity
            
            if (isExist) {
                return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>(200, "exist", null));  // 아이디가 존재하면 중복
            } else {
                return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>(200, "non_exist", null));  // 아이디가 없으면 사용 가능
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiResponse<>(400, "오류", null));
        }
    }

    
    //회원가입 이메일
    @PostMapping("/sendNumber")
    public ResponseEntity<ApiResponse<String>> sendNumber(@RequestBody MemberDTO dto) {
    	String email = dto.getEmail();
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
    @PostMapping("/verifyCode")
    public ResponseEntity<ApiResponse<String>> verifyCode(@RequestBody IdCheckDTO dto) {
    	String email=dto.getEmail();
    	String code=dto.getCode();
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
    @PostMapping("/sendEmailVerificationCode")
    public ResponseEntity<ApiResponse<String>> sendEmailVerificationCode(@RequestBody MemberDTO dto) {
    	String email = dto.getEmail();
    	String name = dto.getName();
    	String id = dto.getId();
        try {
        	System.out.println("인증이메일"+email+"인증이름"+name);
            Map<String, String> map = new HashMap<>();
            map.put("name", name);
            map.put("email", email);
            
        	 // int result = memberService.findIdByEmail(map); dto 
            
            int result = memberService.findIdByEmailEntity(name,email); //Entity 
        	
            if(id != null) {
            	map.put("id", id);
            //	result = memberService.findPwdByEmail(map); //ID포함해서 비밀번호찾기용 검증 재활용
           	result = memberService.findPwdByEmailEntity(name,email,id); //ID포함해서 비밀번호찾기용 검증 재활용 Entity
            }
        	
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
    @PostMapping("/verifyCodeId")
    public ResponseEntity<ApiResponse<String>> verifyCodeId(@RequestBody IdCheckDTO dto) {
    	String email=dto.getEmail();
    	String code=dto.getCode();
    	String name=dto.getName();
        // 만료 시간 확인
    	System.out.println("이메일인증"+email+"이메일인증"+code+"이메일인증"+name);
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
             
           // String id = memberService.findIdByEmail2(map);
   
            String id = memberService.findIdByEmail2Entity(name,email);
            
            
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>(200, id, null));
        } else {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>(204, "not_match", null));
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
        	 String phoneNum = smsRequestDto.getPhoneNum().replaceAll("[^0-9]", "");
            String name =smsRequestDto.getName();
            String id=smsRequestDto.getId();
            
            Map<String, String> map = new HashMap<>();
            map.put("name", name);
            map.put("phone", phoneNum);
            
            System.out.println("아이디"+id+"이름"+name+"번호"+phoneNum);
            //int result = memberService.findIdByPhone(map);
            int result = memberService.findIdByPhoneEntity(name,phoneNum);
            if(id != null) {
            	map.put("id", id);
            	//result = memberService.findPwdByPhone(map); //ID포함해서 비밀번호찾기용 검증 재활용
            	result = memberService.findPwdByPhoneEntity(name,phoneNum,id); //ID포함해서 비밀번호찾기용 검증 재활용
            }
            
        	
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
    
    //회원가입 sms
    @PostMapping("/verifyPhone")
    public ResponseEntity<ApiResponse<String>> verifyPhoneNumberCode(@RequestBody SmsRequestDto smsRequestDto) {
    	String phoneNum =smsRequestDto.getPhoneNum();
    	String code=smsRequestDto.getCode();
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
    
    //아이디찾기 sms
    @PostMapping("/checkPhone")
    public ResponseEntity<ApiResponse<String>> checkPhone(@RequestBody SmsRequestDto dto) {
    	String phone=dto.getPhoneNum();
    	String code=dto.getCode();
    	 String phoneNum = phone.replaceAll("[^0-9]", "");
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
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>(400, "not_match", null));
        }
    }
    
    @PostMapping("/getIdByPhone")
    public ResponseEntity<ApiResponse<String>> checkId(@RequestBody IdFindDTO dto) {
        try {
            System.out.println("Received data: " + dto.getName() + " " + dto.getPhone());

            String name = dto.getName();
            String phone = dto.getPhone().replaceAll("[^0-9]", "");
   
            
            Map<String, String> map = new HashMap<>();
            map.put("name", name);
            map.put("phone", phone);

            //String result = memberService.findIdPhone(map);
            String result = memberService.findIdPhoneEntity(name,phone); //Entity
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

    @PostMapping("/updatepassword")
    public ResponseEntity<ApiResponse<String>> updatepassword(@RequestBody MemberDTO dto) {
        try {
            System.out.println("Received data: " + dto.getId() + " " +dto.getPassword());

            String id = dto.getId();
            String password = dto.getPassword();
   
            
            Map<String, String> map = new HashMap<>();
            map.put("id",id);
            map.put("password", password);

           // int result = memberService.updatePwd(map);
            int result = memberService.updatePwdEntity(id,password); //Entity
            
            System.out.println(result);
            
            if (result == 1) {
                return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(200, "success", null));
            } else {
                return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(404, null, "회원정보없음"));
            }
        } catch (Exception e) {
            System.err.println("Error occurred: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body(new ApiResponse<>(500, null, "에러"));
        }
    }

    
    @CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<String>> login(HttpSession session, @RequestBody MemberDTO dto) {
 
    	try {
            String id = dto.getId();
            String password = dto.getPassword();

            // 로그인 서비스 호출
            int result = memberService.LoginEntity(id, password);

            // 로그인 성공
            if (result == 1) {
            	System.out.println("로그인성공");
            	return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(200, "success", null));
  
            } else {
                // 로그인 실패 (회원 정보 없음)
                return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(404, null, "회원정보없음"));
            }
        } catch (Exception e) {
            // 예외 발생 시 에러 메시지 반환
            System.err.println("Error occurred: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(500, null, "에러"));
        }
    }

    
    @CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<String>> logout(HttpSession session) {
        try {
        	
           System.out.println("11a");
            //session.removeAttribute("id");
        	session.invalidate();
            //System.out.println(session.getAttribute("id"));
            // 로그아웃 성공
                return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(200, "로그아웃", null));
        } catch (Exception e) {
            // 예외 발생 시 에러 메시지 반환
            System.err.println("Error occurred: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body(new ApiResponse<>(500, null, "에러"));
        }
    }


	

	
// -- 지현: 마이페이지(사용자정보) 수정 ---------------------------
    // 세션얻어오기 => 필요없음(2024.11.27)
    @CrossOrigin(origins = "http://localhost:3000/member", allowCredentials = "true")
    @GetMapping("getSession")
    public ResponseEntity<ApiResponse<String>> getSession (HttpSession session) {
    	try {
    		String sessionId = (String) session.getAttribute("id");    		
    		
    		if(sessionId== null) {
    			System.out.println("세션못얻음");
    			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(401, "로그인하지 않은 사용자가 접근하였습니다.", ""));
    		}else {
    			System.out.println(session.getId());
    			return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(200, "세션Id를 가져왔습니다.", sessionId));
    		}			
		} catch (Exception e) {			
			System.err.println("Error occurred: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body(new ApiResponse<>(500, null, "에러"));
		}
    	
    }
    
    
	// 한 명의 사용자 정보를 가져옵니다. (ResponseEntity로 수정하기)
	@GetMapping("getUserInfo/me")
	public ResponseEntity<ApiResponse<MemberDTO>> getUserInfo( HttpSession session) {
		MemberDTO memberDTO = null;
		try {
//			String id = (String) session.getAttribute("id");
			
			String id =authenticationFacade.getCurrentUserId();  // JWT
			
			memberDTO = memberService.getUserInfo(id);
			
			return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(200, "사용자 정보 가져오기", memberDTO));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(500, "사용자정보가져오기 에러", memberDTO));	
		}
	}
	
	// 회원 정보 수정 (ResponseEntity로 수정하기)
	@PutMapping("modifyUserInfo")
	public ResponseEntity<ApiResponse<String>> modifyUserInfo(@RequestBody MemberDTO modifiedData) {
		try {
			System.out.println(modifiedData);
			memberService.modifyUserInfo(modifiedData);
			return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(200, "회원정보수정", "수정성공"));
		} catch (Exception e) {
			System.err.println("Error occurred: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body(new ApiResponse<>(500, null, "에러"));	
		}
	}
	
	// 회원 탈퇴
	@DeleteMapping("infoWithdrawal/me")
	public ResponseEntity<ApiResponse<String>> infoWithdrawal(HttpSession session) {
		try {
//			String id = (String) session.getAttribute("id");
			String id =authenticationFacade.getCurrentUserId();  // JWT
			memberService.infoWithdrawal(id);
//			session.invalidate();
			
			// JWT 토큰 삭제 
			return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(200, "탈퇴", null));
		} catch (Exception e) {
			System.err.println("Error occurred: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body(new ApiResponse<>(500, null, "에러"));
		}
	}
	
	

	// 테스트용 
	@GetMapping("/verify-token")
    public void verifyToken(@RequestHeader("Authorization") String authorizationHeader ,HttpServletRequest request) {
            String token = authorizationHeader.substring(7); 
            
            System.out.println("*verify-token 컨트롤러입니다--------------*");
            System.out.println("액세스 토큰"+token); // 액세스 토큰
            
            Cookie[] cookies = request.getCookies();
            
            System.out.println("리프레쉬 토큰 " + cookies);
    
            Member member = authenticationFacade.getCurrentMember(); // jwt 인증시 로그인된 멤버 엔티티 정보획득

            System.out.println("현재로그인아이디"+member.getId());  // 아이디 가져오는예시 
            System.out.println("현재로그인이메일"+member.getEmail()); // 이메일 예시 가져오는예시 
            System.out.println("현재로그인시퀀스"+member.getMemberSeq());   // 시퀀스 예시 가져오는예시       

    }
	

	// 예약 정보 조회 
	@GetMapping("checkMyBook")
	public ResponseEntity<ApiResponse<List<CheckMyBookDTO>>> checkMyBook(HttpSession session) {
		try {
			String id = (String) session.getAttribute("id");
			System.out.println("checkMyBook : " +id);
			
			List<CheckMyBookDTO> list = memberService.checkMyBook(id);
			
			if(list!=null && !list.isEmpty()) {
				// 예약목록이 존재할 경우, 
				System.out.println("checkMyBookList : " +list);
				return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(200, "예약목록이 있습니다.", list));				
			}else {
				// 예약 목록이 존재하지 않을 경우
				return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(200, "예약목록이 없습니다.", null));
			}
		} catch (Exception e) {
			// 에러났을 경우 
			System.err.println("Error occurred: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body(new ApiResponse<>(500, "예약목록을 불러오는 중 에러 발생", null));
		}
		
	}

	// 특정 년/월의 예약 정보 조회 
	@GetMapping("checkMyBook/checkBookingsByDate")
	public ResponseEntity<ApiResponse<List<CheckMyBookDTO>>> checkBookingsByDate (@RequestParam("classify") String classify, @RequestParam("year") int year, @RequestParam("month") int month, HttpSession session) {
			
		String id =authenticationFacade.getCurrentUserId();  // 아이디 가져오는예시 
		System.out.println("pagination JWT ID : " +id);
		
		try {	
			System.out.println("checkMyBook/checkBookingsByDate : " + id  + ", "+ classify + ", " + year+ ", " + month);
			
			Map<String, Object> map = new HashMap<>();
			map.put("id", id);
			map.put("classify", classify);
			map.put("year", year);
			map.put("month", month);
			
			List<CheckMyBookDTO> list = memberService.checkBookingsByDate(map);
			
			if(list!=null && !list.isEmpty()) {
				// 예약목록이 존재할 경우, 
				System.out.println("checkBookingsByDate : " +list);
				return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(200, "년/월 검색 예약목록이 있습니다.", list));				
			}else {
				// 예약 목록이 존재하지 않을 경우
				return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(200, "년/월 검색 예약목록이 없습니다.", null));
			}
		} catch (Exception e) {
			// 에러났을 경우 
			System.err.println("Error occurred: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body(new ApiResponse<>(500, "예약목록을 불러오는 중 에러 발생", null));
		}
		
	}
	
	
	// 예약정보조회-페이징
	@GetMapping("checkMyBook/pagination")
	public Page<Book> pagination(@RequestParam("currentPage")int currentPage,@RequestParam("classify") String classify, @RequestParam("year") String year, @RequestParam("month") String month, HttpSession session) {
		//String id = (String) session.getAttribute("id");
		
		//JWT 
		String id =authenticationFacade.getCurrentUserId();  
		System.out.println("pagination JWT ID : " +id);

		System.out.println(classify+ year + month);

		int pageSize = 3; // 한 페이지에 보여줄 내용
		Page<Book> pageResult;
		

		// 년월 검색조회
		if (!classify.isEmpty() && !year.isEmpty() && !month.isEmpty()) {
			if(classify.equals("pay_date")) {
				System.out.println("년월조회");
				pageResult = memberService.checkMyBookPagination(id, year, month, currentPage, pageSize);    				
			}else {
				pageResult=null;
			}
		} else {
			// 일반 조회
			System.out.println("일반조회");
			pageResult = memberService.checkMyBookPagination(id, currentPage, pageSize);
		}
		
		System.out.println("[MemberContsroller] pagination111 : "  + pageResult);
		
		return pageResult;
	}
	
	

//	// 예약정보조회-페이징 (되는거)
//	@GetMapping("checkMyBook/pagination")
//	public Page<Book> pagination(@RequestParam("currentPage")int currentPage, HttpSession session) {
//		String id = (String) session.getAttribute("id");
//		
//		int pageSize = 3; // 한 페이지에 보여줄 내용
//	
//		// 일반 조회
//		Page<Book> pageResult = memberService.checkMyBookPagination(id, currentPage, pageSize);
//
//		System.out.println("[MemberContsroller] pagination : "  + pageResult);
//		
//		return pageResult;
//	}
	

	
// 세션 존재 확인 (나중에 지우기)
	@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
	@GetMapping("/session-status" )
	public ResponseEntity<ApiResponse<String>> sessionStatus() {
		
	    String id = authenticationFacade.getCurrentUserId(); // 시큐리티 인증된정보로 멤버 엔티티 정보획득
		
	    if (id == null) {
	    	System.out.println("404");
	        return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                             .body(new ApiResponse<>(404, "세션 없음", null));
	    }
	    
	    System.out.println("200");
	    
	    
	    return ResponseEntity.status(HttpStatus.OK)
	                         .body(new ApiResponse<>(200, "세션 있음", id));		 
		 }
	
	//아이디만 가져갈려고하는것
	@GetMapping("id")
	public ResponseEntity<ApiResponse<String>> getMethodName(HttpSession session) {
	    try {
	        // 현재 로그인한 사용자 정보 가져오기
	        Member member = authenticationFacade.getCurrentMember();
	        
	        if (member == null || member.getId() == null) {
	            // 사용자 정보가 없거나 ID가 없는 경우 처리
	            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
	                                 .body(new ApiResponse<>(401, "로그인이 필요합니다.", null));
	        }

	        System.out.println("현재 로그인 아이디: " + member.getId());
	        System.out.println(member.getId());
	        // 정상적인 경우 아이디 반환
	        return ResponseEntity.status(HttpStatus.OK)
	                             .body(new ApiResponse<>(200, "성공", member.getId()));
	    } catch (Exception e) {
	        // 예외가 발생한 경우 처리
	        System.err.println("에러 발생: " + e.getMessage());
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                             .body(new ApiResponse<>(500, "서버 오류 발생", null));
	    }
	}

	
	

	

}
