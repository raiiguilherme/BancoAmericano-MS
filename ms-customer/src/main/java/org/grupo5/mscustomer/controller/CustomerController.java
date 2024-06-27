package org.grupo5.mscustomer.controller;

import lombok.RequiredArgsConstructor;
import org.grupo5.mscustomer.domain.dtos.CustomerCreateDto;
import org.grupo5.mscustomer.domain.dtos.CustomerResponseDto;
import org.grupo5.mscustomer.service.CustomerService;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class CustomerController {

private final CustomerService customerService;


@PostMapping("/customers")
    public ResponseEntity<CustomerResponseDto> createCustomer(@RequestBody CustomerCreateDto customerDto){
            var customer = customerService.createCustomer(customerDto);
            CustomerResponseDto customerResponseDto = new CustomerResponseDto();
            BeanUtils.copyProperties(customer, customerResponseDto);
            return ResponseEntity.ok(customerResponseDto);

    }
}
