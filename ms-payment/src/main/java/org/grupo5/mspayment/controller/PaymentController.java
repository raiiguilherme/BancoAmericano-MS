package org.grupo5.mspayment.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.grupo5.mspayment.domain.dtos.PaymentCreateDto;
import org.grupo5.mspayment.domain.dtos.PaymentResponseDto;
import org.grupo5.mspayment.service.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class PaymentController {
private final PaymentService paymentService;

    @PostMapping("/payments")
    public ResponseEntity<PaymentResponseDto> createNewPayment(@RequestBody @Valid PaymentCreateDto paymentCreateDto){
        return ResponseEntity.ok(paymentService.createPayment(paymentCreateDto));

    }
}
