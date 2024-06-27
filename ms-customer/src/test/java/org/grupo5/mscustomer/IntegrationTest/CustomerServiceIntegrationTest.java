package org.grupo5.mscustomer.IntegrationTest;

import static org.assertj.core.api.Assertions.assertThat;

import lombok.RequiredArgsConstructor;
import org.grupo5.mscustomer.mocks.CustomerFakeMock;
import org.grupo5.mscustomer.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.Month;


@ActiveProfiles("test")
@SpringBootTest
@Transactional
@DirtiesContext
public class CustomerServiceIntegrationTest {


    @Autowired
    private CustomerFakeMock customerFakeMock;
    @Autowired
    private CustomerService customerService;


    @Test
    public void SaveCustormerWhenDataIsValidAndRetorn200OK(){

    var cotumerDto = customerFakeMock.CustomerDataValid();
   var customer = customerService.createCustomer(cotumerDto);

   assertThat(customer).isNotNull();
   assertThat(customer.getId()).isEqualTo(1L);
   assertThat(customer.getEmail()).isEqualTo("rai@gmail.com");
    assertThat(customer.getBirthday()).hasDayOfMonth(10);
    assertThat(customer.getPoints()).isEqualTo(0);
    assertThat(customer.getBirthday()).hasMonth(Month.of(12));
    assertThat(customer.getBirthday()).hasYear(2000);


    }

}
