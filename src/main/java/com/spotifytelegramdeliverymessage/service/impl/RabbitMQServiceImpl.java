package com.spotifytelegramdeliverymessage.service.impl;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spotifytelegramdeliverymessage.dto.AlbumRelease;
import com.spotifytelegramdeliverymessage.dto.Release;
import com.spotifytelegramdeliverymessage.service.BotService;
import com.spotifytelegramdeliverymessage.service.RabbitMQService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RabbitMQServiceImpl implements RabbitMQService {

    private final ObjectMapper objectMapper;
    private final BotService botService;

    private Map<String, List<AlbumRelease>> releasesMapWithEmail = new HashMap<>();


    public RabbitMQServiceImpl(ObjectMapper objectMapper, BotService botService) {
        this.objectMapper = objectMapper;
        this.botService = botService;
    }


    @RabbitListener(queues = {"${rabbitmq.queue}"})
    private void getMessageFromQueue(String message) {
        try {
            System.out.println("Received message: " + message);
            Release release = objectMapper.readValue(message, Release.class);
            System.out.println("Parsed release: " + release);
            releasesMapWithEmail.put(release.getEmail(), release.getReleaseList());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void sendMessageToUser(String email, String id) throws TelegramApiException {
        System.out.println(releasesMapWithEmail);
        List<AlbumRelease> releaseList = releasesMapWithEmail.get(email);

        String message = releaseList.stream()
                .map(AlbumRelease::getAlbumName)
                .collect(Collectors.joining("\n"));

        if(!message.isEmpty()) {
            botService.sendMessage(id, message);
        } else {
            botService.sendMessage(id, "You have no releases for now");
        }

    }

}
