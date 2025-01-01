package com.spotifytelegramdeliverymessage.controller;

import com.spotifytelegramdeliverymessage.props.BotProps;
import com.spotifytelegramdeliverymessage.service.BotService;
import com.spotifytelegramdeliverymessage.service.RabbitMQService;
import com.spotifytelegramdeliverymessage.service.UserService;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import static com.spotifytelegramdeliverymessage.constant.BotCommands.*;

@Component
public class BotController extends TelegramLongPollingBot {

    private final BotProps botProps;
    private final BotService botService;
    private final UserService userService;
    private final RabbitMQService rabbitMQService;

    private static final Logger logger = LoggerFactory.getLogger(BotController.class);

    public BotController(BotProps botProps, BotService botService, UserService userService, RabbitMQService rabbitMQService) {
        super(botProps.token());
        this.botProps = botProps;
        this.botService = botService;
        this.userService = userService;
        this.rabbitMQService = rabbitMQService;
    }

    @Override
    public String getBotUsername() {
        return botProps.name();
    }



    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        if(!update.hasMessage() || !update.getMessage().hasText()) {
            return;
        }

        String message = update.getMessage().getText();
        String id = update.getMessage().getChatId().toString();
        String username = update.getMessage().getChat().getUserName();


        try {
            switch (message) {
                case START -> {
                    botService.sendWelcomeMessage(id, username);
                }
                case SUBSCRIBE -> {
                    botService.subscribe(id, message);
                }
                case UNSUBSCRIBE -> {
                    botService.unsubscribe(id, message);
                }
                case RELEASE -> {
                    sendInfoReleases();
                }
            }
            if (message.startsWith(REGISTER)) {
                botService.register(id, username, message);
            } else if (message.startsWith(CONFIRM)) {
                botService.confirmation(id, username, message);
            }
        } catch (TelegramApiException e) {
            logger.error("Somethings is wrong: {}", e.getMessage(), e);
        }
    }



    @Scheduled(cron = "0 0 8 * * *")
    private void sendInfoReleases() {
        userService.getAllSubscribeUsers()
                .forEach(user -> {
                    try {
                        rabbitMQService.sendMessageToUser(user.getEmail(), user.getId());
                    } catch (TelegramApiException e) {
                        logger.error("Somethings is wrong: {}", e.getMessage(), e);
                    }
                });
    }
}
