package org.grupo5.mspayment.service;

import lombok.RequiredArgsConstructor;
import org.grupo5.mspayment.domain.Payment;
import org.grupo5.mspayment.domain.dtos.CalculatePostDto;
import org.grupo5.mspayment.domain.dtos.PaymentCreateDto;
import org.grupo5.mspayment.domain.dtos.PaymentResponseDto;
import org.grupo5.mspayment.feingclient.CalculateComunication;
import org.grupo5.mspayment.feingclient.CustomerComunication;
import org.grupo5.mspayment.repository.PaymentRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final CalculateComunication calculateComunication;
    private final CustomerComunication customerComunication;

public PaymentResponseDto createPayment(PaymentCreateDto paymentCreateDto){
    var calculate = calculateComunication.getCalculate(paymentCreateDto.getCategoryId());
    var customer = customerComunication.getCustomer(paymentCreateDto.getCustomerId());
    if (customer==null) throw new RuntimeException("Customer NotFound");
    Payment payment = new Payment();
    payment.setCategory(calculate.getCategory());
    payment.setTotal(paymentCreateDto.getTotal());
    payment.setCustomer_Id(customer.getId());
    payment.setCreated_date(LocalDateTime.now());
    paymentRepository.save(payment);

    //Construindo a requisição post para o mscalculate
    CalculatePostDto calculatePostDto = new CalculatePostDto();
    calculatePostDto.setCategoryId(calculate.getId());
    calculatePostDto.setCustomerId(customer.getId());
    calculatePostDto.setTotal(paymentCreateDto.getTotal());
    calculateComunication.createdCalcule(calculatePostDto);


    //construindo a resposta
    PaymentResponseDto paymentResponseDto = new PaymentResponseDto();

    BeanUtils.copyProperties(payment,paymentResponseDto);
    return paymentResponseDto;




}
}
