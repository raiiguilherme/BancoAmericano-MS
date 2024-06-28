package org.grupo5.mscustomer.mocks;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.grupo5.mscustomer.domain.Customer;
import org.grupo5.mscustomer.domain.dtos.CustomerCreateDto;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@NoArgsConstructor
@Configuration
public class CustomerFakeMock {


    public CustomerCreateDto CustomerDtoDataValid(){
      CustomerCreateDto customer = new CustomerCreateDto();
      customer.setEmail("rai@gmail.com");
      customer.setName("rai");
      customer.setGender("Masculino");
      customer.setCpf("165.880.554-20");
      customer.setBirthday(LocalDate.of(2000,12,10));
      customer.setUrl_photo("urldafoto");
      return customer;
    }

    public Customer CustomerDataValid(){
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setEmail("rai@gmail.com");
        customer.setName("rai");
        customer.setGender("Masculino");
        customer.setCpf("165.880.554-20");
        customer.setBirthday(LocalDate.of(2000,12,10));
        customer.setUrl_photo("urldafoto");
        customer.setPoints(0);
        return customer;
    }


}
