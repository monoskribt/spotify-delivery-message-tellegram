package com.spotifytelegramdeliverymessage.service.impl;


import com.spotifytelegramdeliverymessage.constant.BotCommands;
import com.spotifytelegramdeliverymessage.constant.BotText;
import com.spotifytelegramdeliverymessage.constant.SubscribeUserStatus;
import com.spotifytelegramdeliverymessage.model.User;
import com.spotifytelegramdeliverymessage.service.BotService;
import com.spotifytelegramdeliverymessage.service.EmailService;
import com.spotifytelegramdeliverymessage.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class BotServiceImpl implements BotService {

    @Lazy
    @Autowired
    private AbsSender absSender;

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    private final Map<String, String> emailByChatId = new HashMap<>();

    @Override
    public void sendWelcomeMessage(String id, String username) throws TelegramApiException {
        sendMessage(id, BotText.START_TEXT);
    }

    @Override
    public void subscribe(String id, String username, String message) throws TelegramApiException {
        String email = message.replace(BotCommands.SUBSCRIBE, "").trim();
        int confirmationCode = emailService.generateConfirmationCode();

        User user = new User(id, username);
        user.setCode(String.valueOf(confirmationCode));
        userService.save(user);

        emailByChatId.put(id, email);

        emailService.sendConfirmationEmail(email, confirmationCode);
        sendMessage(id, BotText.CONFIRMATION_TEXT);

        System.out.println(confirmationCode);
    }

    @Override
    public void confirmation(String id, String username, String message) throws TelegramApiException {
        String enteredConfirmationCode = message.replace(BotCommands.CONFIRM, "").trim();

        if(checkCode(enteredConfirmationCode, userService.getCode(id))) {
            String email = emailByChatId.get(id);

            Optional<User> user = userService.findById(id);
            user.get().setEmail(email);
            user.get().setSubscribeUserStatus(SubscribeUserStatus.SUBSCRIBE);
            userService.save(user.get());

            emailByChatId.remove(id);

            sendMessage(id, BotText.SUCCESSFULLY_CONFIRMATION_TEXT);
        }
        else {
            sendMessage(id, BotText.FAILED_CONFIRMATION_TEXT);
        }
    }

    private boolean checkCode(String codeFromUser, String codeFromService) {
        return codeFromUser.equals(codeFromService);
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
