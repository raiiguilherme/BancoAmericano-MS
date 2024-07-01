package org.grupo5.mscalculate.service;

import lombok.RequiredArgsConstructor;
import org.grupo5.mscalculate.domain.Rule;
import org.grupo5.mscalculate.domain.dto.CalculateDto;
import org.grupo5.mscalculate.domain.dto.CalculateResponseDto;
import org.grupo5.mscalculate.domain.dto.RuleCreateDto;
import org.grupo5.mscalculate.repository.CalculateRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CalculateService {

    private final CalculateRepository calculateRepository;


    public Rule createRule(RuleCreateDto ruleCreateDto){
        Rule rule = new Rule();
        BeanUtils.copyProperties(ruleCreateDto, rule);
        calculateRepository.save(rule);
        return rule;

    }

    public List<Rule> getAllRules(){
      return  calculateRepository.findAll();

    }

    public void deleteRuleById(Long id){
     var calculateRule = calculateRepository.findById(id).orElseThrow(
                () -> new RuntimeException("User not found")
        );

     calculateRepository.delete(calculateRule);

    }

    public Rule updateCalculateById(RuleCreateDto ruleCreateDto, Long id){
        var calculateOld = calculateRepository.findById(id).orElseThrow(()->new RuntimeException("Calculate not found"));
        BeanUtils.copyProperties(ruleCreateDto,calculateOld);
        var newCustomer = calculateRepository.save(calculateOld);
        return newCustomer;

    }

    //TODO Fazer o metodo POST que ira calcular o total de pontos

    public CalculateResponseDto calculateValue(CalculateDto calculateDto){

        CalculateResponseDto calculateResponseDto = new CalculateResponseDto();
        var getRule = calculateRepository.findById(calculateDto.getCategory_id()).orElseThrow(()->new RuntimeException("Category not found"));

        int total = getRule.getParity()*calculateDto.getValue();
        calculateResponseDto.setTotal(total);
        return calculateResponseDto;

    }




}
