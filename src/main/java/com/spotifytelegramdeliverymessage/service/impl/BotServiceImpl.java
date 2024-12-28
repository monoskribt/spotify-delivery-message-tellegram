package com.spotifytelegramdeliverymessage.service.impl;


import com.spotifytelegramdeliverymessage.constant.BotCommands;
import com.spotifytelegramdeliverymessage.constant.BotText;
import com.spotifytelegramdeliverymessage.model.User;
import com.spotifytelegramdeliverymessage.service.BotService;
import com.spotifytelegramdeliverymessage.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Service
public class BotServiceImpl implements BotService {

    @Lazy
    @Autowired
    private AbsSender absSender;

    @Autowired
    private UserService userService;


    @Override
    public void sendWelcomeMessage(String id, String username) throws TelegramApiException {
        sendMessage(id, BotText.START_TEXT);
    }

    @Override
    public void subscribe(String id, String username, String message) throws TelegramApiException {
        String email = message.replace(BotCommands.SUBSCRIBE, "").trim();

        User user = new User(id, username);
        user.setEmail(email);
        userService.save(user);
        sendMessage(id, "Thank you! Your email has been saved.");
    }



    private void sendMessage(String id, String text) throws TelegramApiException {
        SendMessage sendMessage = SendMessage.builder()
                .chatId(id)
                .parseMode("Markdown")
                .text(text)
                .build();
        absSender.execute(sendMessage);
    }
}
