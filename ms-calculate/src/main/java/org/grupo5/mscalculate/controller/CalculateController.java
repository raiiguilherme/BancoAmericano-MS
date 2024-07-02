package org.grupo5.mscalculate.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.grupo5.mscalculate.domain.Rule;
import org.grupo5.mscalculate.domain.dto.*;
import org.grupo5.mscalculate.producer.CalculateProducer;
import org.grupo5.mscalculate.service.CalculateService;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class CalculateController {
    private final CalculateService calculateService;
    private final CalculateProducer calculateProducer;
    @PostMapping("/rules")
    public ResponseEntity<RuleResponseDto> postCalculate(@RequestBody @Valid RuleCreateDto ruleCreateDto){
        RuleResponseDto ruleResponseDto = new RuleResponseDto();
        var calculate = calculateService.createRule(ruleCreateDto);
        BeanUtils.copyProperties(calculate, ruleResponseDto);


        return ResponseEntity.ok(ruleResponseDto);

    }

    @GetMapping("/rules")
    public ResponseEntity<List<RuleResponseDto>> getAllCalculate(){

        List<Rule> listOfRule = calculateService.getAllRules();
        List<RuleResponseDto> listOfCalculateDto = new ArrayList<>();
        for (Rule c: listOfRule){
            RuleResponseDto ruleResponseDto = new RuleResponseDto(c);
            listOfCalculateDto.add(ruleResponseDto);
        }

        return ResponseEntity.ok(listOfCalculateDto);
    }

    @DeleteMapping("/rules/{id}")
    public ResponseEntity<String> deleteCalculateById(@PathVariable Long id){
        calculateService.deleteRuleById(id);
        return ResponseEntity.ok("Rule deleted successfully");
    }

    @PutMapping("/rules/{id}")
    public ResponseEntity<RuleResponseDto> updateById(@PathVariable Long id, @RequestBody RuleCreateDto ruleCreateDto){
        RuleResponseDto ruleResponseDto = new RuleResponseDto();
       var calculate = calculateService.updateCalculateById(ruleCreateDto, id);
       BeanUtils.copyProperties(calculate, ruleResponseDto);
       return ResponseEntity.ok(ruleResponseDto);

    }


    @PostMapping("/calculate")//ENDPOINT DE TESTE
    public ResponseEntity<CalculateResponseDto> testeOfPublishMessage(@RequestBody @Valid CalculateDto calculateDto){
           var calcule = calculateService.calculateValue(calculateDto);


            CustomerMessageDto customerMessageDto = new CustomerMessageDto();
            customerMessageDto.setCustomerId(calculateDto.getCustomerId());
            customerMessageDto.setPoints(calcule.getTotal());

            calculateProducer.pushMessageForCustomer(customerMessageDto);
           return ResponseEntity.ok(calcule);
    }

    @GetMapping("/rules/{id}")
    public ResponseEntity<RuleResponseDto> getRuleById(@PathVariable Long id){
        return ResponseEntity.ok(calculateService.findRuleById(id));
    }



}
