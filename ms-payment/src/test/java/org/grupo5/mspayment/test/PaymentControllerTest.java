package org.grupo5.mspayment.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.grupo5.mspayment.controller.PaymentController;
import org.grupo5.mspayment.domain.Payment;
import org.grupo5.mspayment.domain.dtos.CalculateComunicationDto;
import org.grupo5.mspayment.domain.dtos.CustomerComunicationDto;
import org.grupo5.mspayment.domain.dtos.PaymentCreateDto;
import org.grupo5.mspayment.domain.dtos.PaymentResponseDto;
import org.grupo5.mspayment.exceptions.ex.CustomerNotFoundException;
import org.grupo5.mspayment.exceptions.ex.PaymentNotFoundException;
import org.grupo5.mspayment.service.PaymentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PaymentController.class)
@ExtendWith(SpringExtension.class)
public class PaymentControllerTest {

    @MockBean
    private PaymentService paymentService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    private Payment payment;
    private PaymentCreateDto paymentCreateDto;

    private CalculateComunicationDto calculateComunicationDto;
    private CustomerComunicationDto customerComunicationDto;
    private PaymentResponseDto paymentResponseDto;
    UUID id = UUID.randomUUID();

    @BeforeEach
    void setUp(){

        MockitoAnnotations.openMocks(this);
        payment = new Payment();
        payment.setCreated_date(LocalDateTime.now());
        payment.setTotal(2000);
        payment.setCustomer_Id(1L);
        payment.setCategory("Carros");
        payment.setId(id);

        paymentCreateDto = new PaymentCreateDto();
        paymentCreateDto.setTotal(2000);
        paymentCreateDto.setCustomerId(1L);
        paymentCreateDto.setCategoryId(1L);

        calculateComunicationDto = new CalculateComunicationDto();
        calculateComunicationDto.setCategory("Carros");
        calculateComunicationDto.setId(2L);
        calculateComunicationDto.setParity(10);

        customerComunicationDto = new CustomerComunicationDto();
        customerComunicationDto.setId(1L);
        customerComunicationDto.setEmail("rai@gmail.com");
        customerComunicationDto.setName("rai");
        customerComunicationDto.setGender("Masculino");
        customerComunicationDto.setCpf("165.880.554-20");
        customerComunicationDto.setBirthday(LocalDate.of(2000, 12, 10));
        customerComunicationDto.setUrl_photo("urldafoto");
        customerComunicationDto.setPoints(0);

        paymentResponseDto = new PaymentResponseDto();
        paymentResponseDto.setCreated_date(LocalDateTime.now());
        paymentResponseDto.setTotal(2000);
        paymentResponseDto.setCustomer_Id(1L);
        paymentResponseDto.setCategory("Carros");
        paymentResponseDto.setId(id);
    }

    @Test
    void createPaymentWithDataValidAndReturn200() throws Exception {
        when(paymentService.createPayment(any(PaymentCreateDto.class))).thenReturn(paymentResponseDto);

        mockMvc.perform(post("/v1/payments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(paymentCreateDto)))
                .andExpect(status().isOk());
    }

    @Test
    void createPaymentWithDataNotValidAndReturn422() throws Exception {
        paymentCreateDto.setCategoryId(null);

        mockMvc.perform(post("/v1/payments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(paymentCreateDto)))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void getPaymentWithIdValidAndReturn200() throws Exception {

        when(paymentService.getPaymentById(id)).thenReturn(paymentResponseDto);

        mockMvc.perform(get("/v1/payments/{id}",id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    @Test
    void getPaymentWithIdNotValidAndReturn404() throws Exception {

        when(paymentService.getPaymentById(id)).thenThrow(new PaymentNotFoundException("not found"));

        mockMvc.perform(get("/v1/payments/{id}",id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void getPaymentWithCustomerIdValidAndReturn200() throws Exception {

        when(paymentService.getPaymentByCustomerId(anyLong())).thenReturn(paymentResponseDto);

        mockMvc.perform(get("/v1/payments/user/{id}",1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    @Test
    void getPaymentWithCustomerIdNotValidAndReturn404() throws Exception {

        when(paymentService.getPaymentByCustomerId(anyLong())).thenThrow(new CustomerNotFoundException("not found"));

        mockMvc.perform(get("/v1/payments/user/{id}",1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }






}
