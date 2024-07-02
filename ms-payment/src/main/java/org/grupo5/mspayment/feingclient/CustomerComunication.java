package org.grupo5.mspayment.feingclient;

import org.grupo5.mspayment.domain.dtos.CalculateComunicationDto;
import org.grupo5.mspayment.domain.dtos.CustomerComunicationDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-customer",url = "http://localhost:8081")
public interface CustomerComunication {


    @GetMapping("/v1/customers/{id}")
    CustomerComunicationDto getCustomer(@PathVariable("id") Long id);
}
