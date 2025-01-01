package com.spotifytelegramdeliverymessage.repository;


import com.spotifytelegramdeliverymessage.enums.SubscribeStatus;
import com.spotifytelegramdeliverymessage.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    List<User> findAllUsersBySubscribeStatus(SubscribeStatus status);

    boolean existsById(String id);

}
