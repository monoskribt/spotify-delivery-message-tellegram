package com.spotifytelegramdeliverymessage.service;


import com.spotifytelegramdeliverymessage.enums.AccountStatus;
import com.spotifytelegramdeliverymessage.enums.SubscribeStatus;
import com.spotifytelegramdeliverymessage.model.User;

import java.util.List;

public interface UserService {

    void save(User user);

    User findById(String id);

    void  setUserSubscriptionStatus(String id, SubscribeStatus status);

    void setUserAccountStatus(String id, AccountStatus status);

    List<User> getAllSubscribeUsers();

    boolean isAlreadyExist(String id);

    String getCode(String id);
}
