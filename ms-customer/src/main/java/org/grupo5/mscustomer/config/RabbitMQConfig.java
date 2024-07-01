package org.grupo5.mscustomer.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${broker.queue.name}")
    private String queue;

@Bean
    public Queue queue(){
        return new Queue(queue,true);
    }
@Bean
    public Jackson2JsonMessageConverter converter(){
    return new Jackson2JsonMessageConverter();
    }

}
