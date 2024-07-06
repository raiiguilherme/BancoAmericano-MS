package org.grupo5.mspayment.test;

import org.grupo5.mspayment.domain.Payment;
import org.grupo5.mspayment.domain.dtos.CalculateComunicationDto;
import org.grupo5.mspayment.domain.dtos.CustomerComunicationDto;
import org.grupo5.mspayment.domain.dtos.PaymentCreateDto;
import org.grupo5.mspayment.exceptions.ex.CustomerNotFoundException;
import org.grupo5.mspayment.exceptions.ex.PaymentNotFoundException;
import org.grupo5.mspayment.feingclient.CalculateComunication;
import org.grupo5.mspayment.feingclient.CustomerComunication;
import org.grupo5.mspayment.repository.PaymentRepository;
import org.grupo5.mspayment.service.PaymentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PaymentServiceTest {

    @Mock
    private PaymentRepository paymentRepository;
    @Mock
    private  CalculateComunication calculateComunication;
    @Mock
    private  CustomerComunication customerComunication;

    @InjectMocks
    private PaymentService paymentService;

    private Payment payment;
    private PaymentCreateDto paymentCreateDto;

    private CalculateComunicationDto calculateComunicationDto;
    private CustomerComunicationDto customerComunicationDto;

    @BeforeEach
    void setUp(){
        payment = new Payment();
        payment.setCreated_date(LocalDateTime.now());
        payment.setTotal(2000);
        payment.setCustomer_Id(1L);
        payment.setCategory("Carros");
        payment.setId(UUID.randomUUID());

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
    }

    @Test
    void CreatNewPaymentWithDataValid(){
        when(paymentRepository.save(any(Payment.class))).thenReturn(payment);
        when(calculateComunication.getCalculate(anyLong())).thenReturn(calculateComunicationDto);
        when(customerComunication.getCustomer(anyLong())).thenReturn(customerComunicationDto);

        var paymentResponse = paymentService.createPayment(paymentCreateDto);

        verify(paymentRepository, times(1)).save(any(Payment.class));
        verify(calculateComunication, times(1)).getCalculate(anyLong());
        verify(customerComunication, times(1)).getCustomer(anyLong());

        assertEquals(payment.getCategory(), paymentResponse.getCategory());
        assertEquals(payment.getTotal(), paymentResponse.getTotal());
        assertEquals(payment.getCustomer_Id(), paymentResponse.getCustomer_Id());


    }

    @Test
    void CreatNewPaymentWithDataNotValid(){
        when(customerComunication.getCustomer(anyLong())).thenReturn(null);


        assertThrows(CustomerNotFoundException.class, () ->{
            paymentService.createPayment(paymentCreateDto);
        });

        verify(paymentRepository, times(0)).save(any(Payment.class));
        verify(calculateComunication, times(1)).getCalculate(anyLong());
        verify(customerComunication, times(1)).getCustomer(anyLong());

    }

    @Test
    void findPaymentWithIdValid(){
        UUID id = UUID.randomUUID();
        when(paymentRepository.findById(id)).thenReturn(Optional.of(payment));


        var paymentResponse = paymentService.getPaymentById(id);

        verify(paymentRepository, times(1)).findById(id);

        assertEquals(payment.getCategory(), paymentResponse.getCategory());
        assertEquals(payment.getTotal(), paymentResponse.getTotal());
        assertEquals(payment.getCustomer_Id(), paymentResponse.getCustomer_Id());

    }

    @Test
    void findPaymentWithIdNotValid(){
        UUID id = UUID.randomUUID();
        when(paymentRepository.findById(id)).thenReturn(Optional.empty());


        assertThrows(PaymentNotFoundException.class, () ->{
            paymentService.getPaymentById(id);
        });

        verify(paymentRepository, times(1)).findById(id);

    }

    @Test
    void findPaymentWithCustomerIdValid(){

        when(paymentRepository.findPaymentbyCustomerId(anyLong())).thenReturn(payment);


        var paymentResponse = paymentService.getPaymentByCustomerId(1L);

        verify(paymentRepository, times(1)).findPaymentbyCustomerId(anyLong());

        assertEquals(payment.getCategory(), paymentResponse.getCategory());
        assertEquals(payment.getTotal(), paymentResponse.getTotal());
        assertEquals(payment.getCustomer_Id(), paymentResponse.getCustomer_Id());

    }

    @Test
    void findPaymentWithCustomerIdNotValid(){

        when(paymentRepository.findPaymentbyCustomerId(anyLong())).thenReturn(null);


        assertThrows(CustomerNotFoundException.class, () ->{
            paymentService.getPaymentByCustomerId(1L);
        });

        verify(paymentRepository, times(1)).findPaymentbyCustomerId(1L);

    }



}
