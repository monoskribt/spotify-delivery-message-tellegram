package com.spotifytelegramdeliverymessage.controller;

import com.spotifytelegramdeliverymessage.constant.BotCommands;
import com.spotifytelegramdeliverymessage.props.BotProps;
import com.spotifytelegramdeliverymessage.service.BotService;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import static com.spotifytelegramdeliverymessage.constant.BotCommands.*;

@Component
public class BotController extends TelegramLongPollingBot {

    private final BotProps botProps;
    private final BotService botService;

    public BotController(BotProps botProps, BotService botService) {
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

        if(message.startsWith(START)) {
            try {
                botService.sendWelcomeMessage(id, username);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        }
        else if(message.startsWith(SUBSCRIBE)) {
            try {
                botService.subscribe(id, username, message);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
