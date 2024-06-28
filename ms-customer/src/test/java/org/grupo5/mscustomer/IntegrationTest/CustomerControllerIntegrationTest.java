package org.grupo5.mscustomer.IntegrationTest;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.grupo5.mscustomer.mocks.CustomerFakeMock;
import org.grupo5.mscustomer.repository.CustomerRepository;
import org.grupo5.mscustomer.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
@AutoConfigureMockMvc
@DirtiesContext
public class CustomerControllerIntegrationTest {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerFakeMock customerFakeMock;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;


    @BeforeEach
    public void SetUp(){
        customerRepository.deleteAll();
    }

    @Test
    public void CreateCustomerOnAllCorrectDataAndReturnStatus201() throws Exception {

        var customer = customerFakeMock.CustomerDataValid();

        mockMvc.perform(post("/v1/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customer)))
                .andExpect(status().isCreated());

    }

    @Test
    public void CreateCustomerOnIncorrectDataAndReturnStatus422() throws Exception {

        var customerDataInvalid = customerFakeMock.CustomerDataValid();
        customerDataInvalid.setName("a"); //Data in pattern incorrect

        mockMvc.perform(post("/v1/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customerDataInvalid)))
                .andExpect(status().isUnprocessableEntity());

    }

    @Test
    public void getCustomerByIdAndReturnStatus200() throws Exception {
        var customer = customerFakeMock.CustomerDataValid();
        var customerSaved = customerRepository.save(customer);



        mockMvc.perform(get("/v1/customers/"+customerSaved.getId()))
                .andExpect(status().isOk());
    }

    @Test
    public void getCustomerByInvalidIdAndReturnStatus404NotFound() throws Exception {

        mockMvc.perform(get("/v1/customers/"+0L))
                .andExpect(status().isNotFound());
    }
    @Test
    public void getCustomerByIdAndDeleteReturnStatus200() throws Exception {

        var customer = customerFakeMock.CustomerDataValid();
        var customerSaved = customerRepository.save(customer);

        mockMvc.perform(delete("/v1/customers/"+customerSaved.getId()))
                .andExpect(status().isOk());
    }

    @Test
    public void getCustomerByInvalidIdAndDeleteReturnStatus404NotFound() throws Exception {


        mockMvc.perform(delete("/v1/customers/"+0L))
                .andExpect(status().isNotFound());
    }

    @Test  //TODO accomplish the test
    public void updateCustomerOnInvalidDataAndReturnStatus422() throws Exception {
        var customer = customerFakeMock.CustomerDataValid();
        var customerSaved = customerRepository.save(customer);

        var customerRecovery = customerService.getCustomerById(customerSaved.getId());
        customerRecovery.setName("luluu");
        //var customerUpdated = customerRepository.save(customerRecovery);

        System.out.println(customerSaved.getName());
       // System.out.println(customerRecovery.getName());

       // assertThat(customerRecovery.getName()).isNotEqualTo(customerSaved.getName());



      //  mockMvc.perform(put("/v1/customers/"+))
         //       .andExpect(status().isUnprocessableEntity());
    }


}
