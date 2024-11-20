package com.example.demo.service;

import org.springframework.stereotype.Service;
import com.example.demo.dto.member.SmsRequestDto;
import com.example.demo.util.SmsCertificationUtil;

@Service
public class SmsService {

    private final SmsCertificationUtil smsCertificationUtil;

    // Constructor injection
    public SmsService(SmsCertificationUtil smsCertificationUtil) {
        this.smsCertificationUtil = smsCertificationUtil;
    }

    // Sending SMS with a random certification code
    public void sendSms(SmsRequestDto smsRequestDto) {
        String phoneNum = smsRequestDto.getPhoneNum(); // Get phone number from DTO
        String certificationCode = generateCertificationCode(); // Generate a random 6-digit certification code
        smsCertificationUtil.sendSMS(phoneNum, certificationCode); // Send SMS via utility
    }

    // Method to generate a random 6-digit certification code
    private String generateCertificationCode() {
        return String.format("%06d", (int) (Math.random() * 1000000)); // 6-digit code
    }
}
