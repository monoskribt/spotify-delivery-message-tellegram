package com.spotifytelegramdeliverymessage.service.impl;

import com.spotifytelegramdeliverymessage.model.User;
import com.spotifytelegramdeliverymessage.repository.UserRepository;
import com.spotifytelegramdeliverymessage.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void save(User user) {
        userRepository.save(user);
    }
}
