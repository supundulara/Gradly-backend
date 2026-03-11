package com.Gradly.notification_service.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String EVENT_QUEUE = "event.notifications";

    /**
     * Declares the queue as a durable queue.
     * Spring AMQP will automatically create it in RabbitMQ if it doesn't exist yet.
     */
    @Bean
    public Queue eventNotificationsQueue() {
        return new Queue(EVENT_QUEUE, true); // durable = true
    }

    /**
     * RabbitAdmin is responsible for automatically declaring queues/exchanges/bindings
     * on application startup using the beans declared in the context.
     */
    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
