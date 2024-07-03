package org.grupo5.mscustomer.service;

import lombok.RequiredArgsConstructor;
import org.grupo5.mscustomer.domain.Customer;
import org.grupo5.mscustomer.domain.dtos.CustomerCreateDto;
import org.grupo5.mscustomer.domain.dtos.CustomerUpdateDto;
import org.grupo5.mscustomer.exceptions.ex.CustomerNotFoundException;
import org.grupo5.mscustomer.repository.CustomerRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;

    public Customer createCustomer(CustomerCreateDto customerCreateDto){
        var customer = costumerDtoForCostumer(customerCreateDto);
        customerRepository.save(customer);
        return customer;
    }

    public Customer getCustomerById(Long id){
        return customerRepository.findById(id).orElseThrow(
               () -> new CustomerNotFoundException("Usuario nao encontrado")
       );
    }

    public void deleteCustomerById(Long id){
        var customer = getCustomerById(id);
        customerRepository.delete(customer);
    }

    public Customer updateCustomer(Long id, CustomerUpdateDto customerUpdateDto){
       var customerOld = getCustomerById(id);
       BeanUtils.copyProperties(customerUpdateDto,customerOld);
       var newCustomer = customerRepository.save(customerOld);
        return newCustomer;
    }

















    private Customer costumerDtoForCostumer(CustomerCreateDto dto){
        Customer customer = new Customer();
        customer.setCpf(dto.getCpf());
        customer.setName(dto.getName());
        customer.setBirthday(dto.getBirthday());
        customer.setGender(dto.getGender());
        customer.setUrl_photo(dto.getUrl_photo());
        customer.setPoints(0);
        customer.setEmail(dto.getEmail());
        return customer;
    }

    public URI getLocation(Customer customer){
        URI location = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("customers/{id}")
                .buildAndExpand(customer.getId())
                .toUri();
        return location;
    }
}
