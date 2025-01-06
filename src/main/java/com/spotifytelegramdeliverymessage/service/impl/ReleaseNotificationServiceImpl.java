package com.spotifytelegramdeliverymessage.service.impl;

import com.spotifytelegramdeliverymessage.service.RabbitMQService;
import com.spotifytelegramdeliverymessage.service.ReleaseNotificationService;
import com.spotifytelegramdeliverymessage.service.UserService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ReleaseNotificationServiceImpl implements ReleaseNotificationService {

    private final UserService userService;
    private final RabbitMQService rabbitMQService;

    public ReleaseNotificationServiceImpl(UserService userService, RabbitMQService rabbitMQService) {
        this.userService = userService;
        this.rabbitMQService = rabbitMQService;
    }

    @Scheduled(cron = "0 0 8 * * *")
    @Override
    public void sendInfoReleases() {
        userService.getAllSubscribeUsers()
                .forEach(user -> {
                    rabbitMQService.sendMessageToUser(user.getEmail(), user.getId());
                });
    }
}
