package com.spotifytelegramdeliverymessage.service;

import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public interface BotService {

    void sendWelcomeMessage(String id, String username) throws TelegramApiException;

    void subscribe(String id, String username, String message) throws TelegramApiException;

    void confirmation(String id, String username, String message) throws TelegramApiException;

}
