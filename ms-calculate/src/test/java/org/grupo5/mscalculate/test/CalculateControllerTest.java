package org.grupo5.mscalculate.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.grupo5.mscalculate.controller.CalculateController;
import org.grupo5.mscalculate.domain.Rule;
import org.grupo5.mscalculate.domain.dto.CalculateDto;
import org.grupo5.mscalculate.domain.dto.CalculateResponseDto;
import org.grupo5.mscalculate.domain.dto.RuleCreateDto;
import org.grupo5.mscalculate.domain.dto.RuleResponseDto;
import org.grupo5.mscalculate.exceptions.ex.RuleNotFoundException;
import org.grupo5.mscalculate.producer.CalculateProducer;
import org.grupo5.mscalculate.repository.CalculateRepository;
import org.grupo5.mscalculate.service.CalculateService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CalculateController.class)
@ExtendWith(SpringExtension.class)
public class CalculateControllerTest {

    @MockBean
    private CalculateService calculateService;
    @MockBean
    private CalculateProducer calculateProducer;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    private Rule rule;
    private RuleCreateDto ruleCreateDto;
    private RuleCreateDto ruleCreateErrorDto;

    private CalculateDto calculateDto;
    private CalculateResponseDto calculateResponseDto;
    private RuleCreateDto ruleCreateUpdateDto;

    private RuleResponseDto ruleResponseDto;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        rule = new Rule();
        rule.setCategory("Carros");
        rule.setId(1L);
        rule.setParity(10);

        ruleCreateDto = new RuleCreateDto();
        ruleCreateDto.setCategory("Carros");
        ruleCreateDto.setParity(10);

        ruleCreateUpdateDto = new RuleCreateDto();
        ruleCreateUpdateDto.setCategory("Carros");
        ruleCreateUpdateDto.setParity(9);

        ruleCreateErrorDto = new RuleCreateDto();
        ruleCreateErrorDto.setCategory("");
        ruleCreateErrorDto.setParity(10);

        calculateDto = new CalculateDto();
        calculateDto.setTotal(2000);
        calculateDto.setCustomerId(2L);
        calculateDto.setCategoryId(2L);

        calculateResponseDto = new CalculateResponseDto();
        calculateResponseDto.setTotal(20000);

        ruleResponseDto = new RuleResponseDto();
        ruleResponseDto.setParity(10);
        ruleResponseDto.setCategory("Carros");
        ruleResponseDto.setId(1L);




    }

    @Test
    public void CreateRuleOnAllCorrectDataAndReturnStatus200() throws Exception {
       when(calculateService.createRule(any(RuleCreateDto.class))).thenReturn(rule);

        mockMvc.perform(post("/v1/rules")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(rule)))
                .andExpect(status().isOk());

    }

    @Test
    public void CreateRuleOnIncorrectDataAndReturnStatus422() throws Exception {

        mockMvc.perform(post("/v1/rules")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ruleCreateErrorDto)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.errors").exists());

    }

    @Test
    public void getAllRulesAndReturn200() throws Exception {
        when(calculateService.getAllRules()).thenReturn(List.of(rule));

        mockMvc.perform(get("/v1/rules")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    @Test
    public void deleteRuleWithIdValidAndReturn200() throws Exception {

        mockMvc.perform(delete("/v1/rules/{id}",rule.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Rule deleted successfully"));

    }

    @Test
    public void deleteRuleWithIdNotValidAndReturn404() throws Exception {

        doThrow(new RuleNotFoundException("not found")).when(calculateService).deleteRuleById(10L);

        mockMvc.perform(delete("/v1/rules/{id}",10L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

    }


    @Test
    public void updateRuleWithIdAndDataValidAndReturnStatus200() throws Exception {

        when(calculateService.updateCalculateById(any(RuleCreateDto.class),anyLong())).thenReturn(rule);

        mockMvc.perform(put("/v1/rules/{id}",1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ruleCreateUpdateDto)))
                .andExpect(status().isOk());

    }

    @Test
    public void updateCustomerWithIdNotValidAndReturnStatus404() throws Exception {
        when(calculateService.updateCalculateById(any(RuleCreateDto.class),anyLong())).thenThrow(new RuleNotFoundException("not found"));

        mockMvc.perform(put("/v1/rules/{id}",1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ruleCreateUpdateDto)))
                .andExpect(status().isNotFound());

    }

    @Test
    public void sendMessageOnAllCorrectDataAndReturnStatus200() throws Exception {
        when(calculateService.calculateValue(any(CalculateDto.class))).thenReturn(calculateResponseDto);

        mockMvc.perform(post("/v1/calculate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(calculateDto)))
                .andExpect(status().isOk());

    }

    @Test
    public void sendMessageOnIncorrectDataAndReturnStatus404() throws Exception {

        when(calculateService.calculateValue(any(CalculateDto.class))).thenThrow(new RuleNotFoundException("not found"));


        mockMvc.perform(post("/v1/calculate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(calculateDto)))
                .andExpect(status().isNotFound());

    }

    @Test
    public void getRuleByIdValidAndCategoryValidReturn200() throws Exception {
        when(calculateService.findRuleById(anyLong())).thenReturn(ruleResponseDto);

        mockMvc.perform(get("/v1/rules/{id}",1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    @Test
    public void getRuleByIdNotValidAndReturn200() throws Exception {
        when(calculateService.findRuleById(2L)).thenReturn(ruleResponseDto);

        mockMvc.perform(get("/v1/rules/{id}",2L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.parity").value(10));

    }









}
