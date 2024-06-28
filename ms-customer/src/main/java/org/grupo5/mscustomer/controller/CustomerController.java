package org.grupo5.mscustomer.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.grupo5.mscustomer.domain.dtos.CustomerCreateDto;
import org.grupo5.mscustomer.domain.dtos.CustomerResponseDto;
import org.grupo5.mscustomer.domain.dtos.CustomerUpdateDto;
import org.grupo5.mscustomer.service.CustomerService;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class CustomerController {

private final CustomerService customerService;


@PostMapping("/customers")
    public ResponseEntity<CustomerResponseDto> createCustomer(@Valid @RequestBody CustomerCreateDto customerDto){

        var customer = customerService.createCustomer(customerDto);
        CustomerResponseDto customerResponseDto = new CustomerResponseDto();
        BeanUtils.copyProperties(customer, customerResponseDto);
        return ResponseEntity.created(customerService.getLocation(customer)).body(customerResponseDto);

    }

 @GetMapping("/customers/{id}")
    public ResponseEntity<CustomerResponseDto> getCustomerById(@PathVariable Long id){
     var customer = customerService.getCustomerById(id);
     CustomerResponseDto customerResponseDto = new CustomerResponseDto();
     BeanUtils.copyProperties(customer, customerResponseDto);
     return ResponseEntity.ok(customerResponseDto);

}

@DeleteMapping("/customers/{id}")
    public ResponseEntity<String> deleteCustomerById(@PathVariable Long id){
    customerService.deleteCustomerById(id);
    return ResponseEntity.ok().body("Customer Deleted");
}

@PutMapping("/customers/{id}")
    public ResponseEntity<CustomerResponseDto> updateCustomerById(@PathVariable Long id,@Valid @RequestBody CustomerUpdateDto customerUpdateDto){

    var customer = customerService.updateCustomer(id,customerUpdateDto);
    CustomerResponseDto customerResponseDto = new CustomerResponseDto();
    BeanUtils.copyProperties(customer, customerResponseDto);
    return ResponseEntity.ok(customerResponseDto);



}



}
