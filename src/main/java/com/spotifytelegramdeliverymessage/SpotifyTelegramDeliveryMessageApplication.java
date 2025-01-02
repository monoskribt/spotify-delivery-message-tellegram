package com.spotifytelegramdeliverymessage;

import com.spotifytelegramdeliverymessage.props.BotProps;
import com.spotifytelegramdeliverymessage.props.GmailProps;
import com.spotifytelegramdeliverymessage.props.RabbitMQProps;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableConfigurationProperties({BotProps.class, GmailProps.class, RabbitMQProps.class})
@EnableScheduling
public class SpotifyTelegramDeliveryMessageApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpotifyTelegramDeliveryMessageApplication.class, args);
	}

}
