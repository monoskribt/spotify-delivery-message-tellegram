package com.spotifytelegramdeliverymessage.model;

import com.spotifytelegramdeliverymessage.constant.SubscribeUserStatus;
import jakarta.persistence.*;
import lombok.Getter;


@Entity
@Getter
@Table(name = "telegram_user")
public class User {

    @Id
    private String id;

    private String email;

    @Column(nullable = false)
    private String username;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "varchar(255) default 'SUBSCRIBE'")
    private SubscribeUserStatus subscribeUserStatus = SubscribeUserStatus.SUBSCRIBE;

    public User(String id, String username) {
        this.id = id;
        this.username = username;
    }

    public User() {

    }

    public void setEmail(String email) {
        this.email = email;
    }
}
