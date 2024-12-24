package com.example.demo.controller;


import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.view.RedirectView;

import com.example.demo.dto.member.MemberDTO;
import com.example.demo.entity.Member;
import com.example.demo.service.MemberService;
import com.example.demo.util.ApiResponse;

@RestController
@RequestMapping("api/login")
public class OAuthLoginController {

	@Autowired
	MemberService memberService;
	
	 @Value("${spring.security.oauth2.client.registration.naver.client-id}")
	    private String naverClientId;

	    @Value("${spring.security.oauth2.client.registration.naver.client-secret}")
	    private String naverClientSecret;

	    @Value("${spring.security.oauth2.client.registration.naver.redirect-uri}")
	    private String naverRedirectUri;

	    @Value("${spring.security.oauth2.client.provider.naver.authorization-uri}")
	    private String naverAuthorizationUri;

	    @Value("${spring.security.oauth2.client.provider.naver.token-uri}")
	    private String naverTokenUri;

	    @Value("${spring.security.oauth2.client.provider.naver.user-info-uri}")
	    private String naverUserInfoUri;
	    
	    
	    
	    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
	    private String kakaoClientId;

	    @Value("${spring.security.oauth2.client.registration.kakao.client-secret}")
	    private String kakaoClientSecret;

	    @Value("${spring.security.oauth2.client.registration.kakao.redirect-uri}")
	    private String kakaoRedirectUri;

	    @Value("${spring.security.oauth2.client.provider.kakao.authorization-uri}")
	    private String kakaoAuthorizationUri;

	    @Value("${spring.security.oauth2.client.provider.kakao.token-uri}")
	    private String kakaoTokenUri;

	    @Value("${spring.security.oauth2.client.provider.kakao.user-info-uri}")
	    private String kakaoUserInfoUri;
	    
	    
	    
	    @Value("${spring.security.oauth2.client.registration.google.client-id}")
	    private String googleClientId;

	    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
	    private String googleClientSecret;

	    @Value("${spring.security.oauth2.client.registration.google.redirect-uri}")
	    private String googleRedirectUri;

	    @Value("${spring.security.oauth2.client.provider.google.authorization-uri}")
	    private String googleAuthorizationUri;

	    @Value("${spring.security.oauth2.client.provider.google.token-uri}")
	    private String googleTokenUri;

	    @Value("${spring.security.oauth2.client.provider.google.user-info-uri}")
	    private String googleUserInfoUri;
	
	@GetMapping("naver")
	public ResponseEntity<ApiResponse<String>> naverLogin(@RequestParam("code") String authorizationCode,
	                                                      @RequestParam("state") String state) {
	    System.out.println("네이버 인증 코드: " + authorizationCode);
	    System.out.println("네이버 상태값: " + state);

	    // 네이버 Access Token 요청 URL과 사용자 정보 요청 URL

	    // 클라이언트 정보 (네이버 개발자 콘솔에서 발급)

	    RestTemplate restTemplate = new RestTemplate();

	    try {
	        // Access Token 요청
	        HttpHeaders tokenHeaders = new HttpHeaders();
	        tokenHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

	        MultiValueMap<String, String> tokenRequestBody = new LinkedMultiValueMap<>();
	        tokenRequestBody.add("grant_type", "authorization_code");
	        tokenRequestBody.add("client_id", naverClientId);
	        tokenRequestBody.add("client_secret", naverClientSecret);
	        tokenRequestBody.add("redirect_uri", naverRedirectUri);
	        tokenRequestBody.add("code", authorizationCode);
	        tokenRequestBody.add("state", state); // 상태값 추가

	        HttpEntity<MultiValueMap<String, String>> tokenRequest = new HttpEntity<>(tokenRequestBody, tokenHeaders);
	        ResponseEntity<Map> tokenResponse = restTemplate.postForEntity(naverTokenUri, tokenRequest, Map.class);

	        if (tokenResponse.getBody() == null || tokenResponse.getBody().get("access_token") == null) {
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
	                    .body(new ApiResponse<>(400, "토큰 요청 실패", null));
	        }

	        String accessToken = (String) tokenResponse.getBody().get("access_token");
	        System.out.println("Access Token: " + accessToken);

	        // 사용자 정보 요청
	        HttpHeaders userInfoHeaders = new HttpHeaders();
	        userInfoHeaders.add("Authorization", "Bearer " + accessToken);

	        HttpEntity<?> userInfoRequest = new HttpEntity<>(userInfoHeaders);
	        ResponseEntity<Map> userInfoResponse = restTemplate.exchange(naverUserInfoUri, HttpMethod.GET, userInfoRequest, Map.class);

	        Map<String, Object> userInfo = userInfoResponse.getBody();

	        if (userInfo == null || userInfo.isEmpty()) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                    .body(new ApiResponse<>(404, "사용자 정보가 없습니다.", null));
	        }

	        System.out.println("네이버 사용자 정보: " + userInfo);
	        Map<String, Object> response = (Map<String, Object>) userInfo.get("response");
	        
	        String userId = (String) response.get("id");
	        String email = (String) response.get("email");
	        String phone = ((String) response.get("mobile")).replaceAll("[^0-9]", "");
	        String name = (String) response.get("name");
	        System.out.println("네이버 사용자 ID: " + userId);
	        
	        MemberDTO memberDTO=new MemberDTO();
	        memberDTO.setId(userId);
	        memberDTO.setPassword(userId);
	        RedirectView redirectView = new RedirectView();
	        boolean isExist = memberService.checkIdEntity(userId);   // 아이디 중복 체크 entity
	        
	        if(isExist) {
	        	 return ResponseEntity.status(HttpStatus.OK)
        	             .body(new ApiResponse<>(200, "로그인 성공", userId)); // 성공 응답 반환
	        	
        	
	        }
	        else {
	        	System.out.println("회원가입 시도");
	        	//회원가입후 로그인
	        	
	        	Member member = new Member();
	        	member.setId(userId);
	        	member.setEmail(email);
	        	member.setName(name);
	        	member.setPhone(phone);
	        	member.setRole(Member.Role.USER);
	        	member.setPassword(userId);
	        	
	        	int result = memberService.signUp(member);
	        	 return ResponseEntity.status(HttpStatus.OK)
        	             .body(new ApiResponse<>(200, "로그인 성공", userId)); // 성공 응답 반환
	        	
	        }
	       
	       

	    } catch (Exception e) {
	        e.printStackTrace();
	        
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                .body(new ApiResponse<>(500, "서버 오류 발생", null));
	    }
		
	}
	
	@GetMapping("kakao")
	public ResponseEntity<ApiResponse<String>> kakaoLogin(@RequestParam("code") String authorizationCode) {
	    System.out.println("카카오 인증 코드: " + authorizationCode);

	  
	    RestTemplate restTemplate = new RestTemplate();

	    try {
	        // Access Token 요청
	        HttpHeaders tokenHeaders = new HttpHeaders();
	        tokenHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

	        MultiValueMap<String, String> tokenRequestBody = new LinkedMultiValueMap<>();
	        tokenRequestBody.add("grant_type", "authorization_code");
	        tokenRequestBody.add("client_id", kakaoClientId);
	        tokenRequestBody.add("redirect_uri", kakaoRedirectUri);
	        tokenRequestBody.add("code", authorizationCode);

	        HttpEntity<MultiValueMap<String, String>> tokenRequest = new HttpEntity<>(tokenRequestBody, tokenHeaders);
	        ResponseEntity<Map> tokenResponse = restTemplate.postForEntity(kakaoTokenUri, tokenRequest, Map.class);

	        if (tokenResponse.getBody() == null || tokenResponse.getBody().get("access_token") == null) {
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
	                    .body(new ApiResponse<>(400, "토큰 요청 실패", null));
	        }

	        String accessToken = (String) tokenResponse.getBody().get("access_token");
	        System.out.println("Access Token: " + accessToken);

	        
	        // 사용자 정보 요청
	        HttpHeaders userInfoHeaders = new HttpHeaders();
	        userInfoHeaders.add("Authorization", "Bearer " + accessToken);

	        HttpEntity<?> userInfoRequest = new HttpEntity<>(userInfoHeaders);
	        ResponseEntity<Map> userInfoResponse = restTemplate.exchange(kakaoUserInfoUri, HttpMethod.GET, userInfoRequest, Map.class);

	        Map<String, Object> userInfo = userInfoResponse.getBody();

	        if (userInfo == null || userInfo.isEmpty()) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                    .body(new ApiResponse<>(404, "사용자 정보가 없습니다.", null));
	        }

	        System.out.println("카카오 사용자 정보: " + userInfo);

	        // 성공적으로 로그인한 경우
	        return ResponseEntity.ok(new ApiResponse<>(200, "로그인 성공", userInfo.toString()));


	    } catch (Exception e) {
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                .body(new ApiResponse<>(500, "서버 오류 발생", null));
	    }
	}
	
	
	@GetMapping("google")
	public ResponseEntity<ApiResponse<String>> googleLogin(@RequestParam("code") String authorizationCode) {
	    System.out.println("구글 인증 코드: " + authorizationCode);

	    
	    RestTemplate restTemplate = new RestTemplate();

	    try {
	        // Access Token 요청
	        HttpHeaders tokenHeaders = new HttpHeaders();
	        tokenHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

	        MultiValueMap<String, String> tokenRequestBody = new LinkedMultiValueMap<>();
	        tokenRequestBody.add("grant_type", "authorization_code");
	        tokenRequestBody.add("client_id", googleClientId);
	        tokenRequestBody.add("client_secret", googleClientSecret);
	        tokenRequestBody.add("redirect_uri", googleRedirectUri);
	        tokenRequestBody.add("code", authorizationCode);

	        HttpEntity<MultiValueMap<String, String>> tokenRequest = new HttpEntity<>(tokenRequestBody, tokenHeaders);
	        ResponseEntity<Map> tokenResponse = restTemplate.postForEntity(googleTokenUri, tokenRequest, Map.class);

	        if (tokenResponse.getBody() == null || tokenResponse.getBody().get("access_token") == null) {
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
	                    .body(new ApiResponse<>(400, "토큰 요청 실패", null));
	        }

	        String accessToken = (String) tokenResponse.getBody().get("access_token");
	        System.out.println("Access Token: " + accessToken);

	        // 사용자 정보 요청
	        HttpHeaders userInfoHeaders = new HttpHeaders();
	        userInfoHeaders.add("Authorization", "Bearer " + accessToken);

	        HttpEntity<?> userInfoRequest = new HttpEntity<>(userInfoHeaders);
	        ResponseEntity<Map> userInfoResponse = restTemplate.exchange(googleUserInfoUri, HttpMethod.GET, userInfoRequest, Map.class);

	        Map<String, Object> userInfo = userInfoResponse.getBody();

	        if (userInfo == null || userInfo.isEmpty()) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                    .body(new ApiResponse<>(404, "사용자 정보가 없습니다.", null));
	        }

	        System.out.println("구글 사용자 정보: " + userInfo);
	       
	        // 사용자 정보에서 필요한 값 추출
	        String userId = (String) userInfo.get("sub");
	        String name = (String) userInfo.get("name");
	        String email = (String) userInfo.get("email");
	        String phone = ((String) userInfo.get("mobile"));
	        phone = phone != null ? phone.replaceAll("[^0-9]", "") : "0";
	        
	        System.out.println("네이버 사용자 ID: " + userId);
	        
	        MemberDTO memberDTO=new MemberDTO();
	        memberDTO.setId(userId);
	        memberDTO.setPassword(userId);
	        RedirectView redirectView = new RedirectView();
	        boolean isExist = memberService.checkIdEntity(userId);   // 아이디 중복 체크 entity
	        
	        if(isExist) {
	        	 return ResponseEntity.ok(new ApiResponse<>(200, "로그인 성공", userId));
	        }
	        else {
	        	System.out.println("회원가입 시도");
	        	//회원가입후 로그인
	        	
	        	Member member = new Member();
	        	member.setId(userId);
	        	member.setEmail(email);
	        	member.setName(name);
	        	member.setPhone(phone);
	        	member.setRole(Member.Role.USER);
	        	member.setPassword(userId);
	        	
	        	int result = memberService.signUp(member);
	        	if (result == 1) {
		        	
	        		 return ResponseEntity.ok(new ApiResponse<>(200, "회원가입 성공", userId));
	        		
	        		
	            }
	        	else {
	        		 return ResponseEntity.status(HttpStatus.BAD_REQUEST)
	        	                .body(new ApiResponse<>(400, "회원가입을 실패했습니다.", null));
	        	}
	        	
	        }
	        

	    } catch (Exception e) {
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                .body(new ApiResponse<>(500, "서버 오류 발생", null));
	    }
	}

	 
}
