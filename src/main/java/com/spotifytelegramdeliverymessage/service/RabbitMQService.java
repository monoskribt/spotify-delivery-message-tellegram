package com.spotifytelegramdeliverymessage.service;

public interface RabbitMQService {

    void sendMessageToUser(String email, String id);

}
