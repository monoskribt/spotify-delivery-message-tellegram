package com.spotifytelegramdeliverymessage.service;

public interface EmailService {

    int generateConfirmationCode();

    void sendConfirmationEmail(String toEmail, int confirmationCode);

}
