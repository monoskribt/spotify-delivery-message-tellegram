package com.spotifytelegramdeliverymessage.service.impl;

import com.spotifytelegramdeliverymessage.service.EmailService;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;

    public EmailServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void sendConfirmationEmail(String toEmail, int confirmationCode) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Confirmation Code");
        message.setText("Your confirmation code is: " + confirmationCode);

        javaMailSender.send(message);
    }

    @Override
    public int generateConfirmationCode() {
        int lowerLimit = 100000;
        int higherLimit = 999999;

        return lowerLimit + new Random().nextInt(higherLimit - lowerLimit + 1);
    }
}
