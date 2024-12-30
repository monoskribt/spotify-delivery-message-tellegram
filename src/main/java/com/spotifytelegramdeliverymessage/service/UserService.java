package com.spotifytelegramdeliverymessage.service;


import com.spotifytelegramdeliverymessage.constant.SubscribeUserStatus;
import com.spotifytelegramdeliverymessage.model.User;

import java.util.Optional;

public interface UserService {

    void save(User user);

    Optional<User> findById(String id);

    void setSubscriptionStatus(String id, SubscribeUserStatus status);

    String getCode(String id);
}
