package com.example.demo.service;

import org.springframework.stereotype.Service;
import com.example.demo.dto.member.SmsRequestDto;
import com.example.demo.util.SmsCertificationUtil;

@Service
public class SmsService {

    private final SmsCertificationUtil smsCertificationUtil;

    public SmsService(SmsCertificationUtil smsCertificationUtil) {
        this.smsCertificationUtil = smsCertificationUtil;
    }

    /**
     * SMS 전송 요청
     * @param phoneNum 수신자 전화번호
     * @return 생성된 인증 코드
     */
    public String sendSms(String phoneNum) {
        String certificationCode = generateCertificationCode(); // 인증 코드 생성
        smsCertificationUtil.sendSMS(phoneNum, certificationCode); // SMS 전송
        return certificationCode; // 인증 코드 반환
    }

    /**
     * SMS 전송 요청 DTO 기반
     * @param smsRequestDto SMS 요청 정보
     * @return 생성된 인증 코드
     */
    public String sendSms(SmsRequestDto smsRequestDto) {
        return sendSms(smsRequestDto.getPhoneNum());
    }

    /**
     * 6자리 인증 코드 생성
     * @return 6자리 인증 코드
     */
    private String generateCertificationCode() {
        return String.format("%06d", (int) (Math.random() * 1000000)); // 6자리 랜덤 숫자
    }
}
