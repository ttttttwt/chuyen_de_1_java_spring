package com.example.chuyen_de_1.service;

import jakarta.mail.MessagingException;

public interface IOtpService {
    String generatedOtp(String userId);

    boolean checkOtp(String optCode, String userId);

    public void sendOtp(String email, String optCode) throws MessagingException;

    public void deleteOtp(String userId);
}
