package org.grupo5.mscustomer.Test;

import org.grupo5.mscustomer.consumers.CustomerConsumer;
import org.grupo5.mscustomer.domain.Customer;
import org.grupo5.mscustomer.domain.dtos.CalculateDto;
import org.grupo5.mscustomer.repository.CustomerRepository;
import org.grupo5.mscustomer.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ConsumerTest {

    @InjectMocks
    private CustomerConsumer customerConsumer;
    @Mock
    private CustomerService customerService;
    @Mock
    private CustomerRepository customerRepository;

   private CalculateDto calculateDto;
    private Customer customer;
    @BeforeEach
    void setUp(){
       // MockitoAnnotations.openMocks(this);
       calculateDto = new CalculateDto();
        calculateDto.setCustomerId(1L);
        calculateDto.setPoints(1000);

        customer = new Customer();
        customer.setId(1L);
        customer.setEmail("rai@gmail.com");
        customer.setName("rai");
        customer.setGender("Masculino");
        customer.setCpf("165.880.554-20");
       customer.setBirthday(LocalDate.of(2000, 12, 10));
        customer.setUrl_photo("http://s3.url/" + UUID.randomUUID() + ".PNG");
        customer.setPoints(0);
    }
    @Test
    void getMessageAndSaveCustomer(){


        when(customerService.getCustomerById(1L)).thenReturn(customer);

        // When
        customerConsumer.consumesMessageAndSaveAllPoints(calculateDto);

        // Then
        ArgumentCaptor<Customer> customerCaptor = ArgumentCaptor.forClass(Customer.class);
        verify(customerRepository).save(customerCaptor.capture());
        Customer savedCustomer = customerCaptor.getValue();

        assertEquals(1L, savedCustomer.getId());
        assertEquals(1000, savedCustomer.getPoints());

    }



}
