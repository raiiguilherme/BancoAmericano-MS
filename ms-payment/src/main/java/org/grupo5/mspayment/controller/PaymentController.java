package org.grupo5.mspayment.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.grupo5.mspayment.domain.dtos.PaymentCreateDto;
import org.grupo5.mspayment.domain.dtos.PaymentResponseDto;
import org.grupo5.mspayment.exceptions.MessageError;
import org.grupo5.mspayment.service.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class PaymentController {
private final PaymentService paymentService;


    @Operation(summary = "create payment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "created with success.",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "422", description = "fields not valid ",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageError.class)))
    })

    @PostMapping("/payments")
    public ResponseEntity<PaymentResponseDto> createNewPayment(@RequestBody @Valid PaymentCreateDto paymentCreateDto){
        return ResponseEntity.ok(paymentService.createPayment(paymentCreateDto));

    }

    @Operation(summary = "get payment by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "get with success.",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "not found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageError.class))),
            @ApiResponse(responseCode = "409", description = "this id must be UUID type",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageError.class)))
    })
    @GetMapping("/payments/{id}")
    public ResponseEntity<PaymentResponseDto> getPaymentById(@PathVariable UUID id){
        return ResponseEntity.ok(paymentService.getPaymentById(id));

    }

    @Operation(summary = "get payment by customer id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "get with success.",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "not found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageError.class)))
    })
    @GetMapping("/payments/user/{id}")
    public ResponseEntity<PaymentResponseDto> getPaymentByCustomerId(@PathVariable Long id){
        return ResponseEntity.ok(paymentService.getPaymentByCustomerId(id));

    }





}
