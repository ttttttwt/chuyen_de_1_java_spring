package com.example.chuyen_de_1.service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Service
public class OtpService implements IOtpService {

    private int otpLength = 6;
    private int otpExpiredTime = 5;

    private LoadingCache<String, Integer> otpCache;

    @Autowired
    private JavaMailSender javaMailSender;

    public OtpService() {
        otpCache = CacheBuilder.newBuilder()
                .expireAfterWrite(otpExpiredTime, TimeUnit.MINUTES)
                .build(CacheLoader.from(key -> 0));
    }

    @Override
    public String generatedOtp(String userId) {
        SecureRandom secureRandom;
        try {
            secureRandom = SecureRandom.getInstanceStrong();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        StringBuilder generatedOTP = new StringBuilder(otpLength);
        for (int i = 0; i < otpLength; i++) {
            generatedOTP.append(secureRandom.nextInt(10));
        }

        otpCache.put(userId, Integer.valueOf(generatedOTP.toString()));
        return generatedOTP.toString();
    }

    @Override
    public boolean checkOtp(String otpCode, String userId) {
        try {
            Integer cachedOtp = otpCache.get(userId);
            return cachedOtp.equals(Integer.valueOf(otpCode));
        } catch (ExecutionException e) {
            return false;
        }
    }

    @Override
    public void sendOtp(String email, String otpCode) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(message, "utf-8");

        String htmlMessage = "<div style=\"font-family: Helvetica,Arial,sans-serif;min-width:1000px;overflow:auto;line-height:2\">\n" +
                "  <div style=\"margin:50px auto;width:70%;padding:20px 0\">\n" +
                "    <div style=\"border-bottom:1px solid #eee\">\n" +
                "      <a href=\"\" style=\"font-size:1.4em;color: #00466a;text-decoration:none;font-weight:600\">Quoc Forum</a>\n" +
                "    </div>\n" +
                "    <p style=\"font-size:1.1em\">Hi,</p>\n" +
                "    <p>Thank you for choosing we forum. Use the following OTP to complete your Register. OTP is valid for 5 minutes</p>\n" +
                "    <h2 style=\"background: #00466a;margin: 0 auto;width: max-content;padding: 0 10px;color: #fff;border-radius: 4px;\">" + otpCode + "</h2>\n" +
                "    <p style=\"font-size:0.9em;\">Regards,<br />Quoc Le</p>\n" +
                "    <hr style=\"border:none;border-top:1px solid #eee\" />\n" +
                "    <div style=\"float:right;padding:8px 0;color:#aaa;font-size:0.8em;line-height:1;font-weight:300\">\n" +
                "      <p>Quoc Forum</p>\n" +
                "      <p>470 Trần Đại Nghĩa</p>\n" +
                "      <p>Đà Nẵng</p>\n" +
                "    </div>\n" +
                "  </div>\n" +
                "</div>";

        messageHelper.setTo(email);
        messageHelper.setSubject("OTP for register");
        messageHelper.setText(htmlMessage, true);

        javaMailSender.send(message);
    }

    @Override
    public void deleteOtp(String userId) {
        otpCache.invalidate(userId);
    }


}