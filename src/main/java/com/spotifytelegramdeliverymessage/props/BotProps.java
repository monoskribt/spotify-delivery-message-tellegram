package com.spotifytelegramdeliverymessage.props;

import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(prefix = "bot")
public record BotProps(String name, String token) {

}
