package org.grupo5.mspayment.feingclient;

import org.grupo5.mspayment.domain.dtos.CalculateComunicationDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-calculate",url = "http://localhost:8082")
public interface CalculateComunication{


    @GetMapping("/v1/rules/{id}")
    CalculateComunicationDto getCalculate(@PathVariable("id") Long id);
}
