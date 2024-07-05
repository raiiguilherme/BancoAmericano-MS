package org.grupo5.mscustomer.Test;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.grupo5.mscustomer.controller.CustomerController;
import org.grupo5.mscustomer.domain.Customer;
import org.grupo5.mscustomer.domain.dtos.CustomerCreateDto;
import org.grupo5.mscustomer.domain.dtos.CustomerUpdateDto;
import org.grupo5.mscustomer.exceptions.ex.CustomerNotFoundException;
import org.grupo5.mscustomer.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CustomerController.class)
@ExtendWith(SpringExtension.class)
public class CustomerControllerTest {
    private CustomerCreateDto customerCreateDto;
    private CustomerCreateDto customerCreateDtoError;
    private Customer customer;
    private CustomerUpdateDto customerUpdateDto;
    private CustomerUpdateDto customerUpdateDtoError;



    @MockBean
    private CustomerService customerService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;


    @BeforeEach
    public void SetUp(){

        MockitoAnnotations.openMocks(this);


        customerCreateDto = new CustomerCreateDto();
        customerCreateDto.setEmail("rai@gmail.com");
        customerCreateDto.setName("rai");
        customerCreateDto.setGender("Masculino");
        customerCreateDto.setCpf("165.880.554-20");
        customerCreateDto.setBirthday(LocalDate.of(2000, 12, 10));
        customerCreateDto.setUrl_photo("urldafoto");

        customerCreateDtoError = new CustomerCreateDto();
        customerCreateDtoError.setEmail("rai");
        customerCreateDtoError.setName("r");
        customerCreateDtoError.setGender("Mo");
        customerCreateDtoError.setCpf("14-20");
        customerCreateDtoError.setBirthday(LocalDate.of(2000, 12, 10));
        customerCreateDtoError.setUrl_photo("to");

        customerUpdateDtoError = new CustomerUpdateDto();
        customerCreateDtoError.setEmail("rai");
        customerCreateDtoError.setName("r");
        customerCreateDtoError.setGender("Mo");
        customerCreateDtoError.setCpf("14-20");
        customerCreateDtoError.setBirthday(LocalDate.of(2000, 12, 10));
        customerCreateDtoError.setUrl_photo("to");

        customer = new Customer();
        customer.setId(1L);
        customer.setEmail("rai@gmail.com");
        customer.setName("rai");
        customer.setGender("Masculino");
        customer.setCpf("165.880.554-20");
        customer.setBirthday(LocalDate.of(2000, 12, 10));
        customer.setUrl_photo("urldafoto");
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
    public void CreateCustomerOnAllCorrectDataAndReturnStatus201() throws Exception {
        when(customerService.createCustomer(any(CustomerCreateDto.class))).thenReturn(customer);

        mockMvc.perform(post("/v1/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customer)))
                .andExpect(status().isCreated());

    }
    @Test
    public void CreateCustomerOnIncorrectDataAndReturnStatus422() throws Exception {

        mockMvc.perform(post("/v1/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customerCreateDtoError)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.errors").exists());

    }

    @Test
    public void findCustomerWithIdValidAndReturnStatus200() throws Exception {
        when(customerService.getCustomerById(anyLong())).thenReturn(customer);

        mockMvc.perform(get("/v1/customers/{id}",customer.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    @Test
    public void findCustomerWithIdNotValidAndReturnStatus404() throws Exception {
        when(customerService.getCustomerById(10L)).thenThrow(new CustomerNotFoundException("not found"));

        mockMvc.perform(get("/v1/customers/{id}",10L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

    }

    @Test
    public void deleteCustomerWithIdValidAndReturnStatus200() throws Exception {


        mockMvc.perform(delete("/v1/customers/{id}",customer.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Customer Deleted"));

    }

    @Test
    public void deleteCustomerWithIdNotValidAndReturnStatus404() throws Exception {
        doThrow(new CustomerNotFoundException("not found")).when(customerService).deleteCustomerById(10L);//WHEN METHOD IS VOID RETURN

        mockMvc.perform(delete("/v1/customers/{id}",10L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

    }

    @Test
    public void updateCustomerWithIdAndDataValidAndReturnStatus200() throws Exception {
        when(customerService.updateCustomer(anyLong(), any(CustomerUpdateDto.class))).thenReturn(customer);

        mockMvc.perform(put("/v1/customers/{id}",1L)
                        .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(customerUpdateDto)))
                .andExpect(status().isOk());

    }

    @Test
    public void updateCustomerWithIdNotValidAndReturnStatus404() throws Exception {
        when(customerService.updateCustomer(anyLong(), any(CustomerUpdateDto.class))).thenThrow(new CustomerNotFoundException("Not found"));

        mockMvc.perform(put("/v1/customers/{id}",1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customerUpdateDto)))
                .andExpect(status().isNotFound());

    }

    @Test
    public void updateCustomerWithDataNotValidAndReturnStatus422() throws Exception {
        mockMvc.perform(put("/v1/customers/{id}",1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customerUpdateDtoError)))
                .andExpect(status().isUnprocessableEntity());

    }






}
