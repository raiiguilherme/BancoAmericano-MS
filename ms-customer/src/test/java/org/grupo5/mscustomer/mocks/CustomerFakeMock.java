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


    public CustomerCreateDto CustomerDataValid(){
      CustomerCreateDto customer = new CustomerCreateDto();
      customer.setEmail("rai@gmail.com");
      customer.setName("rai");
      customer.setGender("Male");
      customer.setCpf("165.880.554-20");
      customer.setBirthday(LocalDate.of(2000,12,10));
      customer.setUrl_photo("urldafoto");
      return customer;
    }


}
