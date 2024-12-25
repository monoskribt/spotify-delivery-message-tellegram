package com.spotifytelegramdeliverymessage;

import com.spotifytelegramdeliverymessage.props.BotProps;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({BotProps.class})
public class SpotifyTelegramDeliveryMessageApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpotifyTelegramDeliveryMessageApplication.class, args);
	}

}
