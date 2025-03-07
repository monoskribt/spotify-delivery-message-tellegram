package com.spotifytelegramdeliverymessage.config;

import com.spotifytelegramdeliverymessage.props.GmailProps;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
@RequiredArgsConstructor
public class GmailConfig {

    private final GmailProps gmailProps;

    @Bean
    public JavaMailSender setJavaMailSender() {
        JavaMailSenderImpl javaMailSenderImpl = new JavaMailSenderImpl();

        javaMailSenderImpl.setHost("smtp.gmail.com");
        javaMailSenderImpl.setPort(587);
        javaMailSenderImpl.setUsername(gmailProps.username());
        javaMailSenderImpl.setPassword(gmailProps.password());
        javaMailSenderImpl.setProtocol("smtp");

        Properties properties = javaMailSenderImpl.getJavaMailProperties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.debug", "true");

        return javaMailSenderImpl;
    }
}
