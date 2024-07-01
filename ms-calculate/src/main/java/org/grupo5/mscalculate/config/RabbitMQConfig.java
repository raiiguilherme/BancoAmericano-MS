package org.grupo5.mscalculate.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public Jackson2JsonMessageConverter messageConverter(){//METODO QUE CONVERTE DE JSON PARA O TIPO "JAVA"
        ObjectMapper objectMapper = new ObjectMapper();
        return  new Jackson2JsonMessageConverter(objectMapper);
    }
}
