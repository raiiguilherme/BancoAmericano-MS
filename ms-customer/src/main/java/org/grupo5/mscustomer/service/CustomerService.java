package org.grupo5.mscustomer.service;

import lombok.RequiredArgsConstructor;
import org.grupo5.mscustomer.domain.Customer;
import org.grupo5.mscustomer.domain.dtos.CustomerCreateDto;
import org.grupo5.mscustomer.repository.CustomerRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;

    public Customer createCustomer(CustomerCreateDto customerCreateDto){
        var customer = costumerDtoForCostumer(customerCreateDto);
        customerRepository.save(customer);
        return customer;
    }




    private Customer costumerDtoForCostumer(CustomerCreateDto dto){
        Customer customer = new Customer();
        customer.setCpf(dto.getCpf());
        customer.setName(dto.getName());
        customer.setBirthday(dto.getBirthday());
        customer.setGender(dto.getGender());
        customer.setUrl_photo(dto.getUrl_photo());
        customer.setPoints(0L);
        customer.setEmail(dto.getEmail());
        return customer;
    }
}