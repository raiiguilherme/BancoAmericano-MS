package org.grupo5.mscustomer.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.grupo5.mscustomer.domain.dtos.CustomerCreateDto;
import org.grupo5.mscustomer.domain.dtos.CustomerResponseDto;
import org.grupo5.mscustomer.domain.dtos.CustomerUpdateDto;
import org.grupo5.mscustomer.exceptions.MessageError;
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

    @Operation(summary = "create customer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "created with success.",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "422", description = "fields not valid or cpf or email already exists",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageError.class)))
    })
@PostMapping("/customers")
    public ResponseEntity<CustomerResponseDto> createCustomer(@Valid @RequestBody CustomerCreateDto customerDto){

        var customer = customerService.createCustomer(customerDto);
        CustomerResponseDto customerResponseDto = new CustomerResponseDto();
        BeanUtils.copyProperties(customer, customerResponseDto);
        return ResponseEntity.created(customerService.getLocation(customer)).body(customerResponseDto);

    }

    @Operation(summary = "get customer by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "get with success.",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "customer not found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageError.class)))
    })

 @GetMapping("/customers/{id}")
    public ResponseEntity<CustomerResponseDto> getCustomerById(@PathVariable Long id){
     var customer = customerService.getCustomerById(id);
     CustomerResponseDto customerResponseDto = new CustomerResponseDto();
     BeanUtils.copyProperties(customer, customerResponseDto);
     return ResponseEntity.ok(customerResponseDto);

}
    @Operation(summary = "delete customer by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "delete with success.",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "customer not found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageError.class)))
    })

@DeleteMapping("/customers/{id}")
    public ResponseEntity<String> deleteCustomerById(@PathVariable Long id){
    customerService.deleteCustomerById(id);
    return ResponseEntity.ok().body("Customer Deleted");
}
    @Operation(summary = "update customer by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "updated with success.",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "customer not found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageError.class))),
            @ApiResponse(responseCode = "422", description = "fields not valid",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageError.class)))


    })

@PutMapping("/customers/{id}")
    public ResponseEntity<CustomerResponseDto> updateCustomerById(@PathVariable Long id,@Valid @RequestBody CustomerUpdateDto customerUpdateDto){

    var customer = customerService.updateCustomer(id,customerUpdateDto);

    CustomerResponseDto customerResponseDto = new CustomerResponseDto();
    BeanUtils.copyProperties(customer, customerResponseDto);
    return ResponseEntity.ok(customerResponseDto);



}



}
