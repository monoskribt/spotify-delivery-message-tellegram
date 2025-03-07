package com.spotifytelegramdeliverymessage.service.impl;

import com.spotifytelegramdeliverymessage.enums.AccountStatus;
import com.spotifytelegramdeliverymessage.constant.BotCommands;
import com.spotifytelegramdeliverymessage.constant.BotText;
import com.spotifytelegramdeliverymessage.enums.SubscribeStatus;
import com.spotifytelegramdeliverymessage.model.User;
import com.spotifytelegramdeliverymessage.service.BotService;
import com.spotifytelegramdeliverymessage.service.EmailService;
import com.spotifytelegramdeliverymessage.service.MessageSender;
import com.spotifytelegramdeliverymessage.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Service
@RequiredArgsConstructor
public class BotServiceImpl implements BotService {

    private final UserService userService;
    private final EmailService emailService;
    private final MessageSender messageSender;

    @Override
    public void sendWelcomeMessage(String id, String username) throws TelegramApiException {
        if(!userService.isAlreadyExist(id)) {
            messageSender.sendMessage(id, BotText.START_TEXT);
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
            messageSender.sendMessage(id, BotText.CONFIRMATION_TEXT);

            System.out.println(confirmationCode);
        } else {
            messageSender.sendMessage(id, "You are already register");
        }
    }

    @Override
    public void confirmation(String id, String username, String message) throws TelegramApiException {
        String enteredConfirmationCode = message.replace(BotCommands.CONFIRM, "").trim();

        if(userService.isAlreadyExist(id) &&
                userService.getUserAccountStatus(id, AccountStatus.NOT_CONFIRMED)) {
            if(checkCode(enteredConfirmationCode, userService.getCode(id))) {

                userService.setUserSubscriptionStatus(id, SubscribeStatus.SUBSCRIBE);
                userService.setUserAccountStatus(id, AccountStatus.CONFIRMED);

                messageSender.sendMessage(id, BotText.SUCCESSFULLY_CONFIRMATION_TEXT);
            }
            else {
                messageSender.sendMessage(id, BotText.FAILED_CONFIRMATION_TEXT);
            }
        } else {
            messageSender.sendMessage(id, "You are already register");
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
}
