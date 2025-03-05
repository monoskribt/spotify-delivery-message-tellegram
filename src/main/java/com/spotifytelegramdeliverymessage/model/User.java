package com.spotifytelegramdeliverymessage.model;

import com.spotifytelegramdeliverymessage.enums.AccountStatus;
import com.spotifytelegramdeliverymessage.enums.SubscribeStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "telegram_user")
@Getter
@Setter
@NoArgsConstructor
public class User {

    @Id
    private String id;

    private String email;

    @Column(nullable = false)
    private String username;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "varchar(255) default 'UNSUBSCRIBE'")
    private SubscribeStatus subscribeStatus = SubscribeStatus.UNSUBSCRIBE;

    private String code;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "varchar(255) default 'NOT_CONFIRMED'")
    private AccountStatus accountStatus = AccountStatus.NOT_CONFIRMED;

    public User(String id, String username) {
        this.id = id;
        this.username = username;
    }
}
