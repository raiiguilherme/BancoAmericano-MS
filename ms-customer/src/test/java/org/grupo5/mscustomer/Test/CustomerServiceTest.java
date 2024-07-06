package org.grupo5.mscustomer.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import com.amazonaws.services.s3.AmazonS3;
import org.grupo5.mscustomer.AWS.S3Config;
import org.grupo5.mscustomer.domain.Customer;
import org.grupo5.mscustomer.domain.dtos.CustomerCreateDto;
import org.grupo5.mscustomer.domain.dtos.CustomerUpdateDto;
import org.grupo5.mscustomer.exceptions.ex.CustomerNotFoundException;
import org.grupo5.mscustomer.repository.CustomerRepository;
import org.grupo5.mscustomer.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Base64;
import java.util.Optional;
import java.util.UUID;


@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private AmazonS3 amazonS3;

    @InjectMocks
    private CustomerService customerService;

    private CustomerCreateDto customerCreateDto;
    private CustomerCreateDto customerCreateDtoError;
    private CustomerUpdateDto customerUpdateDto;

    private Customer customer;

    @BeforeEach
    public void setUp() {

        customerCreateDto = new CustomerCreateDto();
        customerCreateDto.setEmail("rai@gmail.com");
        customerCreateDto.setName("rai");
        customerCreateDto.setGender("Masculino");
        customerCreateDto.setCpf("165.880.554-20");
        customerCreateDto.setBirthday(LocalDate.of(2000, 12, 10));
        customerCreateDto.setPhotoBase64(Base64.getEncoder().encodeToString("testImage".getBytes()));

        customerCreateDtoError = new CustomerCreateDto();
        customerCreateDtoError.setEmail("rai");
        customerCreateDtoError.setName("r");
        customerCreateDtoError.setGender("Mo");
        customerCreateDtoError.setCpf("14-20");
        customerCreateDtoError.setBirthday(LocalDate.of(2000, 12, 10));
        customerCreateDtoError.setPhotoBase64("photobase64");

        customer = new Customer();
        customer.setId(1L);
        customer.setEmail("rai@gmail.com");
        customer.setName("rai");
        customer.setGender("Masculino");
        customer.setCpf("165.880.554-20");
        customer.setBirthday(LocalDate.of(2000, 12, 10));
        customer.setUrl_photo("http://s3.url/" + UUID.randomUUID() + ".PNG");
        customer.setPoints(0);

        customerUpdateDto = new CustomerUpdateDto();
        customerUpdateDto.setEmail("rai@gmail.com");
        customerUpdateDto.setName("rai");
        customerUpdateDto.setGender("Masculino");
        customerUpdateDto.setCpf("165.880.554-21");
        customerUpdateDto.setBirthday(LocalDate.of(2000, 12, 10));
        customerUpdateDto.setUrl_photo("urldafoto");
    }






    @Test
    void saveCustomerWithDataValid() throws MalformedURLException {



        when(amazonS3.putObject(anyString(), anyString(), any(File.class))).thenReturn(null);
        when(amazonS3.getUrl(anyString(), anyString())).thenReturn(new URL(customer.getUrl_photo()));
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);


        var savedCustomer = customerService.createCustomer(customerCreateDto);
        savedCustomer.setId(1L);

        verify(customerRepository, times(1)).save(any(Customer.class));

        // verify data is correct
        assertEquals(customer.getId(), savedCustomer.getId());
        assertEquals(customer.getCpf(), savedCustomer.getCpf());
        assertEquals(customer.getName(), savedCustomer.getName());
        assertEquals(customer.getEmail(), savedCustomer.getEmail());
        assertEquals(customer.getBirthday(), savedCustomer.getBirthday());
        assertEquals(customer.getGender(), savedCustomer.getGender());
    }

    @Test
    void findCustomerByIdValid(){
        when(customerRepository.findById(anyLong())).thenReturn(Optional.of(customer));

       var customerReturned = customerService.getCustomerById(1L);

        verify(customerRepository, times(1)).findById(anyLong());

        assertEquals(customer.getId(), customerReturned.getId());
        assertEquals(customer.getCpf(), customerReturned.getCpf());
        assertEquals(customer.getName(), customerReturned.getName());
        assertEquals(customer.getEmail(), customerReturned.getEmail());
        assertEquals(customer.getBirthday(), customerReturned.getBirthday());
        assertEquals(customer.getGender(), customerReturned.getGender());


    }

    @Test
    void findCustomerByIdNotValid(){
        when(customerRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(CustomerNotFoundException.class, () ->{
                    customerService.getCustomerById(anyLong());
        });

        verify(customerRepository, times(1)).findById(anyLong());


    }

    @Test
    void deleteCustomerByIdValid(){
        when(customerRepository.findById(anyLong())).thenReturn(Optional.of(customer));
        doNothing().when(customerRepository).delete(any(Customer.class));

        customerService.deleteCustomerById(1L);

        verify(customerRepository, times(1)).findById(anyLong());
        verify(customerRepository, times(1)).delete(any(Customer.class));


    }
    @Test
    void deleteCustomerByIdNotValid(){
        when(customerRepository.findById(anyLong())).thenReturn(Optional.empty());


        assertThrows(CustomerNotFoundException.class, () ->{
            customerService.deleteCustomerById(10L);
        });

        verify(customerRepository, times(1)).findById(anyLong());
        verify(customerRepository, times(0)).delete(any(Customer.class));


    }

    @Test
    void updateCustomerTest() {
        when(customerRepository.findById(anyLong())).thenReturn(Optional.of(customer));
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        Customer updatedCustomer = customerService.updateCustomer(1L, customerUpdateDto);

        verify(customerRepository, times(1)).findById(anyLong());
        verify(customerRepository, times(1)).save(any(Customer.class));


        assertEquals(customer.getId(), updatedCustomer.getId());
        assertEquals(customerUpdateDto.getCpf(), updatedCustomer.getCpf());
        assertEquals(customerUpdateDto.getName(), updatedCustomer.getName());
        assertEquals(customerUpdateDto.getEmail(), updatedCustomer.getEmail());
        assertEquals(customerUpdateDto.getBirthday(), updatedCustomer.getBirthday());
        assertEquals(customerUpdateDto.getGender(), updatedCustomer.getGender());
    }

    @Test
    void updateCustomerNotFoundErrorTest() {
        when(customerRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(CustomerNotFoundException.class, () ->{
            customerService.updateCustomer(1L, customerUpdateDto);
        });

        verify(customerRepository, times(1)).findById(anyLong());
        verify(customerRepository, times(0)).save(any(Customer.class));



    }





    }








