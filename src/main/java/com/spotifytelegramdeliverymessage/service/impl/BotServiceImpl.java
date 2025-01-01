package com.spotifytelegramdeliverymessage.service.impl;


import com.spotifytelegramdeliverymessage.enums.AccountStatus;
import com.spotifytelegramdeliverymessage.constant.BotCommands;
import com.spotifytelegramdeliverymessage.constant.BotText;
import com.spotifytelegramdeliverymessage.enums.SubscribeStatus;
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

@Service
public class BotServiceImpl implements BotService {

    @Lazy
    @Autowired
    private AbsSender absSender;

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;


    @Override
    public void sendWelcomeMessage(String id, String username) throws TelegramApiException {
        if(!userService.isAlreadyExist(id)) {
            sendMessage(id, BotText.START_TEXT);
        }
    }

    @Override
    public void register(String id, String username, String message) throws TelegramApiException {
        if(!userService.isAlreadyExist(id)) {
            String email = message.replace(BotCommands.REGISTER, "").trim();
            int confirmationCode = emailService.generateConfirmationCode();

            User user = new User(id, username);
            user.setEmail(email);
            user.setCode(String.valueOf(confirmationCode));
            userService.save(user);

            emailService.sendConfirmationEmail(email, confirmationCode);
            sendMessage(id, BotText.CONFIRMATION_TEXT);

            System.out.println(confirmationCode);
        } else {
            sendMessage(id, "You are already register");
        }
    }

    @Override
    public void confirmation(String id, String username, String message) throws TelegramApiException {
        String enteredConfirmationCode = message.replace(BotCommands.CONFIRM, "").trim();

        if(!userService.isAlreadyExist(id)) {
            if(checkCode(enteredConfirmationCode, userService.getCode(id))) {

                userService.setUserSubscriptionStatus(id, SubscribeStatus.SUBSCRIBE);
                userService.setUserAccountStatus(id, AccountStatus.CONFIRMED);

                sendMessage(id, BotText.SUCCESSFULLY_CONFIRMATION_TEXT);
            }
            else {
                sendMessage(id, BotText.FAILED_CONFIRMATION_TEXT);
            }
        } else {
            sendMessage(id, "You are already register");
        }
    }

    @Override
    public void subscribe(String id, String message) {
        userService.setUserSubscriptionStatus(id, SubscribeStatus.SUBSCRIBE);
    }


    @Override
    public void unsubscribe(String id, String message) {
        userService.setUserSubscriptionStatus(id, SubscribeStatus.UNSUBSCRIBE);
    }

    private boolean checkCode(String codeFromUser, String codeFromService) {
        return codeFromUser.equals(codeFromService);
    }

    @Override
    public void sendMessage(String id, String text) throws TelegramApiException {
        SendMessage sendMessage = SendMessage.builder()
                .chatId(id)
                .parseMode("Markdown")
                .text(text)
                .build();
        absSender.execute(sendMessage);
    }
}
