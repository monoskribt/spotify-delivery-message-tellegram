package com.spotifytelegramdeliverymessage.service.impl;

import com.spotifytelegramdeliverymessage.constant.SubscribeUserStatus;
import com.spotifytelegramdeliverymessage.exception.UserNotFoundException;
import com.spotifytelegramdeliverymessage.model.User;
import com.spotifytelegramdeliverymessage.repository.UserRepository;
import com.spotifytelegramdeliverymessage.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public Optional<User> findById(String id) {
        return Optional.of(userRepository
                .findById(id))
                .orElseThrow(() -> new UserNotFoundException("User is not present with id: " + id));
    }

    @Override
    public String getCode(String id) {
        Optional<User> user = findById(id);
        return user.get().getCode();
    }

}
