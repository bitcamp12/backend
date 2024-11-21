package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.util.Random;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    // 이메일로 랜덤 번호를 보내는 메서드
    public String sendRandomNumberEmail(String toEmail) throws MailException, MessagingException {
        // 랜덤 6자리 숫자 생성
        Random random = new Random();
        int randomNumber = 100000 + random.nextInt(900000);  // 6자리 랜덤 번호 생성

        String subject = "인증번호";  // 이메일 제목
        String body = "인증번호는 " + randomNumber + "입니다.";  // 이메일 본문

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setTo(toEmail);  // 받는 이메일
        helper.setSubject(subject);  // 이메일 제목
        helper.setText(body, true); // 이메일 본문 (HTML로 전송 가능)

        javaMailSender.send(mimeMessage);  // 이메일 전송

        return String.valueOf(randomNumber); // 생성된 랜덤 번호 반환
    }
}
