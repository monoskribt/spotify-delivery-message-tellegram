package com.spotifytelegramdeliverymessage.controller;

import com.spotifytelegramdeliverymessage.props.BotProps;
import com.spotifytelegramdeliverymessage.service.BotService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import static com.spotifytelegramdeliverymessage.constant.BotCommands.*;

@Component
@Slf4j
public class BotController extends TelegramLongPollingBot {

    private final BotProps botProps;
    private final BotService botService;

    public BotController(BotProps botProps,
                         @Lazy BotService botService) {
        super(botProps.token());
        this.botProps = botProps;
        this.botService = botService;
    }

    @Override
    public String getBotUsername() {
        return botProps.name();
    }

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
                case START -> botService.sendWelcomeMessage(id, username);

                case SUBSCRIBE -> botService.subscribe(id, message);

                case UNSUBSCRIBE -> botService.unsubscribe(id, message);
            }
            if (message.startsWith(REGISTER)) {
                botService.register(id, username, message);
            } else if (message.startsWith(CONFIRM)) {
                botService.confirmation(id, username, message);
            }
        } catch (TelegramApiException e) {
            log.warn("Somethings is wrong: {}", e.getCause(), e);
        }
    }
}
