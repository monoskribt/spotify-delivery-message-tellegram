package com.spotifytelegramdeliverymessage.service;

import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public interface MessageSender {

    void sendMessage(String id, String text) throws TelegramApiException;
}
