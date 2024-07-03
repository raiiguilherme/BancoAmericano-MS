package org.grupo5.mspayment.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.grupo5.mspayment.domain.dtos.PaymentCreateDto;
import org.grupo5.mspayment.domain.dtos.PaymentResponseDto;
import org.grupo5.mspayment.service.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class PaymentController {
private final PaymentService paymentService;

    @PostMapping("/payments")
    public ResponseEntity<PaymentResponseDto> createNewPayment(@RequestBody @Valid PaymentCreateDto paymentCreateDto){
        return ResponseEntity.ok(paymentService.createPayment(paymentCreateDto));

    }

    @GetMapping("/payments/{id}")
    public ResponseEntity<PaymentResponseDto> getPaymentById(@PathVariable UUID id){
        return ResponseEntity.ok(paymentService.getPaymentById(id));

    }

    @GetMapping("/payments/user/{id}")
    public ResponseEntity<PaymentResponseDto> getPaymentByCustomerId(@PathVariable Long id){
        return ResponseEntity.ok(paymentService.getPaymentByCustomerId(id));

    }





}
