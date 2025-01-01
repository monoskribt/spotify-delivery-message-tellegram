package com.spotifytelegramdeliverymessage.service;

import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public interface RabbitMQService {

    void sendMessageToUser(String email, String id) throws TelegramApiException;

}
