package org.grupo5.mscalculate.service;

import lombok.RequiredArgsConstructor;
import org.grupo5.mscalculate.domain.Rule;
import org.grupo5.mscalculate.domain.dto.CalculateDto;
import org.grupo5.mscalculate.domain.dto.CalculateResponseDto;
import org.grupo5.mscalculate.domain.dto.RuleCreateDto;
import org.grupo5.mscalculate.domain.dto.RuleResponseDto;
import org.grupo5.mscalculate.exceptions.ex.RuleNotFoundException;
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
    public RuleResponseDto findRuleById(Long id){

       if (calculateRepository.findById(id).isEmpty()){ //WHEN CATEGORY NOT EXISTS
           RuleResponseDto ruleResponseDto = new RuleResponseDto();
           ruleResponseDto.setId(id);
           ruleResponseDto.setCategory("without category");
           ruleResponseDto.setParity(1);
           return ruleResponseDto;
       }
       else { //WHEN CATEGORY EXISTS
           var rule = calculateRepository.findById(id).get();
           return new RuleResponseDto(rule);
       }

    }

    public List<Rule> getAllRules(){
      return  calculateRepository.findAll();

    }

    public void deleteRuleById(Long id){
     var calculateRule = calculateRepository.findById(id).orElseThrow(
                () -> new RuleNotFoundException("Rule not found")
        );

     calculateRepository.delete(calculateRule);

    }

    public Rule updateCalculateById(RuleCreateDto ruleCreateDto, Long id){
        var calculateOld = calculateRepository.findById(id).orElseThrow(()->new RuleNotFoundException("Rule not found"));
        BeanUtils.copyProperties(ruleCreateDto,calculateOld);
        return calculateRepository.save(calculateOld);

    }





    public CalculateResponseDto calculateValue(CalculateDto calculateDto){

        CalculateResponseDto calculateResponseDto = new CalculateResponseDto();

        if (calculateRepository.findById(calculateDto.getCategoryId()).isEmpty()){ //WHEN CATEGORY NOT EXISTS
            int total = calculateDto.getTotal();
            calculateResponseDto.setTotal(total);
            return calculateResponseDto;
        }
        else {//WHEN CATEGORY EXISTS
            var getRule = calculateRepository.findById(calculateDto.getCategoryId()).get();

            int total = getRule.getParity() * calculateDto.getTotal();
            calculateResponseDto.setTotal(total);
            return calculateResponseDto;
        }

    }




}
