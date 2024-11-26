package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    private static final Logger LOGGER = Logger.getLogger(EmailService.class.getName());

    // 이메일로 랜덤 번호를 보내는 메서드
    public String sendRandomNumberEmail(String toEmail) {
        try {
            // 랜덤 6자리 숫자 생성
            Random random = new Random();
            int randomNumber = 100000 + random.nextInt(900000); // 6자리 랜덤 번호 생성

            String subject = "인증번호"; // 이메일 제목
            String body = "인증번호는 " + randomNumber + "입니다."; // 이메일 본문

            // MimeMessage 객체 생성 및 설정
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setTo(toEmail); // 받는 이메일
            helper.setSubject(subject); // 이메일 제목
            helper.setText(body, true); // 이메일 본문 (HTML 가능)

            // 이메일 전송
            javaMailSender.send(mimeMessage);
            LOGGER.info("이메일이 성공적으로 전송되었습니다: " + toEmail);

            return String.valueOf(randomNumber); // 생성된 랜덤 번호 반환

        } catch (MailException e) {
            LOGGER.log(Level.SEVERE, "이메일 전송 실패 (MailException): " + e.getMessage(), e);
            return "이메일 전송 중 오류가 발생했습니다: " + e.getMessage();
        } catch (MessagingException e) {
            LOGGER.log(Level.SEVERE, "이메일 전송 실패 (MessagingException): " + e.getMessage(), e);
            return "이메일 메시지 설정 중 오류가 발생했습니다: " + e.getMessage();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "이메일 전송 중 알 수 없는 오류 발생: " + e.getMessage(), e);
            return "예기치 못한 오류가 발생했습니다: " + e.getMessage();
        }
    }
}
