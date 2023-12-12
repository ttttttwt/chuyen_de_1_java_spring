package com.example.chuyen_de_1.controller;

import com.example.chuyen_de_1.service.OtpService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/v1/mail")
public class MailController {

    @Autowired
    JavaMailSender javaMailSender;

    @Autowired
    OtpService otpService;

    @GetMapping("/say_hello")
    public String sayHello() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("leanh202223@gmail.com");
        message.setSubject("hello from quocla.21it@vku.udn.vn");
        message.setText("hello leanh202223@gmail.com");

        javaMailSender.send(message);
        return "success";
    }

    @GetMapping("/test_otp")
    public String testOtp() throws MessagingException {
        otpService.sendOtp("leanh202223@gmail.com", "123456");
        return "success";
    }
}
