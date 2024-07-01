package org.grupo5.mscalculate.producer;

import lombok.RequiredArgsConstructor;
import org.grupo5.mscalculate.domain.dto.CustomerMessageDto;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CalculateProducer {


    private final RabbitTemplate rabbitTemplate;

    @Value("${broker.queue.name}")
    private String routingkey;

    public void pushMessageForCustomer(CustomerMessageDto customerMessageDto){



        //COMO SERA UTILIZADO EXCHANGE DEFAULT, BASTA ADICIONAR UMA STRING VAZIA
        //ADICIONANDO TAMBEM A ROUTINGKEY
        //ADICIONANDO TAMBEM A MENSAGEM
        rabbitTemplate.convertAndSend("",routingkey,customerMessageDto);

    }
}
