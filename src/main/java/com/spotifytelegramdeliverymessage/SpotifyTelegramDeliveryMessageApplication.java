package com.spotifytelegramdeliverymessage;

import com.spotifytelegramdeliverymessage.props.BotProps;
import com.spotifytelegramdeliverymessage.props.GmailProps;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({BotProps.class, GmailProps.class})
public class SpotifyTelegramDeliveryMessageApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpotifyTelegramDeliveryMessageApplication.class, args);
	}

}
