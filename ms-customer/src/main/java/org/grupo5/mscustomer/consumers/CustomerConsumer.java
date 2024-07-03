package org.grupo5.mscustomer.consumers;

import lombok.RequiredArgsConstructor;
import org.grupo5.mscustomer.domain.dtos.CalculateDto;
import org.grupo5.mscustomer.repository.CustomerRepository;
import org.grupo5.mscustomer.service.CustomerService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class CustomerConsumer {

private final CustomerService customerService;
private final CustomerRepository customerRepository;

@RabbitListener(queues = "${broker.queue.name}")
public void consumesMessageAndSaveAllPoints(@Payload CalculateDto calculateDto){

      var customer = customerService.getCustomerById(calculateDto.getCustomerId());
      Integer customerAllPoints = customer.getPoints()+ calculateDto.getPoints();
      customer.setPoints(customerAllPoints);
      customerRepository.save(customer);
      System.out.println("chegou aqui");



}

}
