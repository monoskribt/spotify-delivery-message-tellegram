package com.spotifytelegramdeliverymessage.props;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "rabbitmq")
public record RabbitMQProps(String exchange, String queue, String routingKey) {

}
