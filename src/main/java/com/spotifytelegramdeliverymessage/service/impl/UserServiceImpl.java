package com.spotifytelegramdeliverymessage.service.impl;

import com.spotifytelegramdeliverymessage.enums.AccountStatus;
import com.spotifytelegramdeliverymessage.enums.SubscribeStatus;
import com.spotifytelegramdeliverymessage.exception.UserNotFoundException;
import com.spotifytelegramdeliverymessage.model.User;
import com.spotifytelegramdeliverymessage.repository.UserRepository;
import com.spotifytelegramdeliverymessage.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Consumer;

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
    public User findById(String id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User is not found with id: " + id));
    }

    @Override
    public void setUserSubscriptionStatus(String id, SubscribeStatus status) {
        updateUserFunctions(id, user -> user.setSubscribeStatus(status));
    }

    @Override
    public void setUserAccountStatus(String id, AccountStatus status) {
        updateUserFunctions(id, user -> user.setAccountStatus(status));
    }

    @Override
    public List<User> getAllSubscribeUsers() {
        return userRepository.findAllUsersBySubscribeStatus(SubscribeStatus.SUBSCRIBE);
    }

    @Override
    public void deleteUserById(String id) {
        userRepository.deleteById(id);
    }

    @Override
    public String getCode(String id) {
        return findById(id).getCode();
    }

    private void updateUserFunctions(String id, Consumer<User> updateUser) {
        User user = findById(id);
        updateUser.accept(user);
        userRepository.save(user);
    }

}
