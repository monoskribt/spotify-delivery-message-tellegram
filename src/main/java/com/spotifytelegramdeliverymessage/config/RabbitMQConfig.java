package com.spotifytelegramdeliverymessage.config;

import com.spotifytelegramdeliverymessage.props.RabbitMQProps;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    private final RabbitMQProps rabbitMQProps;

    public RabbitMQConfig(RabbitMQProps rabbitMQProps) {
        this.rabbitMQProps = rabbitMQProps;
    }

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(rabbitMQProps.exchange());
    }

    @Bean
    public Queue queue() {
        return new Queue(rabbitMQProps.queue(), true);
    }

    @Bean
    public Binding binding(DirectExchange directExchange, Queue queue) {
        return BindingBuilder.bind(queue)
                .to(directExchange).with(rabbitMQProps.routingKey());
    }

}
