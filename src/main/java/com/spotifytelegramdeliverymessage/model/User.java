package com.spotifytelegramdeliverymessage.model;

import com.spotifytelegramdeliverymessage.constant.AccountStatus;
import com.spotifytelegramdeliverymessage.constant.SubscribeUserStatus;
import jakarta.persistence.*;


@Entity
@Table(name = "telegram_user")
public class User {

    @Id
    private String id;

    private String email;

    @Column(nullable = false)
    private String username;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "varchar(255) default 'UNSUBSCRIBE'")
    private SubscribeUserStatus subscribeUserStatus = SubscribeUserStatus.UNSUBSCRIBE;

    private String code;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "varchar(255) default 'NOT_CONFIRMED'")
    private AccountStatus accountStatus = AccountStatus.NOT_CONFIRMED;

    public User(String id, String username) {
        this.id = id;
        this.username = username;
    }

    public User() {

    }

    public void setId(String id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setSubscribeUserStatus(SubscribeUserStatus subscribeUserStatus) {
        this.subscribeUserStatus = subscribeUserStatus;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setAccountStatus(AccountStatus accountStatus) {
        this.accountStatus = accountStatus;
    }

    public String getCode() {
        return code;
    }
}
