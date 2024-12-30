package com.spotifytelegramdeliverymessage.service;


import com.spotifytelegramdeliverymessage.model.User;

import java.util.Optional;

public interface UserService {

    void save(User user);

    Optional<User> findById(String id);

    String getCode(String id);
}
