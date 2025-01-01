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
        addReleasesTest();
    }

    private void addReleasesTest() {
        List<AlbumRelease> list1 = new ArrayList<>();
        List<AlbumRelease> list2 = new ArrayList<>();

        AlbumRelease albumRelease1 = new AlbumRelease("123", "First Release Test-1");
        AlbumRelease albumRelease2 = new AlbumRelease("456", "Second Release Test-2");

        list1.add(albumRelease1);
        list2.add(albumRelease2);

        releasesMapWithEmail.put("user@gmail.com", list1);
        releasesMapWithEmail.put("verificator@gmail.com", list2);
        releasesMapWithEmail.put("developer.job98@gmail.com", list2);
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
