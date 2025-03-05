package com.spotifytelegramdeliverymessage.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spotifytelegramdeliverymessage.dto.AlbumRelease;
import com.spotifytelegramdeliverymessage.dto.Release;
import com.spotifytelegramdeliverymessage.exception.SendingMessageException;
import com.spotifytelegramdeliverymessage.service.BotService;
import com.spotifytelegramdeliverymessage.service.MessageSender;
import com.spotifytelegramdeliverymessage.service.RabbitMQService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class RabbitMQServiceImpl implements RabbitMQService {

    private final ObjectMapper objectMapper;
    private final MessageSender messageSender;

    private final Map<String, List<AlbumRelease>> releasesMapWithEmail = new HashMap<>();

    @RabbitListener(queues = {"${rabbitmq.queue}"})
    private void getMessageFromQueue(String message) {
        try {
            log.info("Received message: {}", message);
            Release release = objectMapper.readValue(message, Release.class);

            releasesMapWithEmail.computeIfAbsent(release.getEmail(), rls -> new ArrayList<>())
                    .addAll(release.getReleaseList());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void sendMessageToUser(String email, String id) {
        Optional<List<AlbumRelease>> releaseList = Optional.ofNullable(releasesMapWithEmail.get(email));
        releaseList.ifPresentOrElse(list -> {
            String message = list.stream()
                    .map(AlbumRelease::getAlbumName)
                    .collect(Collectors.joining("\n"));
            try {
                messageSender.sendMessage(id, message);
                releasesMapWithEmail.remove(email);
            } catch (TelegramApiException e) {
                log.info("Received raw message: {}", message);
                throw new SendingMessageException("You got error connected with sending message: " + e.getCause());
            }
        },
        () -> {
            try {
                messageSender.sendMessage(id, "You have no releases for now");
            } catch (TelegramApiException e) {
                throw new SendingMessageException("You got error connected with sending message: " + e.getMessage());
            }
        });
    }
}
