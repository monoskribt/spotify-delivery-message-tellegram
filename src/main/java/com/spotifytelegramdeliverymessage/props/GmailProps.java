package com.spotifytelegramdeliverymessage.props;

import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(prefix = "gmail")
public record GmailProps(String username, String password) {

}
