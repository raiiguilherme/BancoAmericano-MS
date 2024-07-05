package org.grupo5.mscalculate.test;

import jakarta.validation.ConstraintViolationException;
import org.grupo5.mscalculate.domain.Rule;
import org.grupo5.mscalculate.domain.dto.CalculateDto;
import org.grupo5.mscalculate.domain.dto.RuleCreateDto;
import org.grupo5.mscalculate.domain.dto.RuleResponseDto;
import org.grupo5.mscalculate.exceptions.ex.RuleNotFoundException;
import org.grupo5.mscalculate.repository.CalculateRepository;
import org.grupo5.mscalculate.service.CalculateService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CalculateServiceTest {

    @Mock
    private CalculateRepository calculateRepository;
    @InjectMocks
    private CalculateService calculateService;

    private Rule rule;
    private RuleCreateDto ruleCreateDto;
    private RuleCreateDto ruleCreateErrorDto;

    private CalculateDto calculateDto;
    private RuleCreateDto ruleCreateUpdateDto;

    @BeforeEach
    void setUp(){
        //MockitoAnnotations.openMocks(this);
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


    }

    @Test
    void createNewRuleWithDataValid(){
        when(calculateRepository.save(any(Rule.class))).thenReturn(rule);

        var ruleSaved = calculateService.createRule(ruleCreateDto);
        ruleSaved.setId(1L);

        verify(calculateRepository, times(1)).save(any(Rule.class));

        assertEquals(rule.getId(), ruleSaved.getId());
        assertEquals(rule.getParity(), ruleSaved.getParity());
        assertEquals(rule.getCategory(), ruleSaved.getCategory());


    }
    @Test
    void findRuleByIdValid(){
        when(calculateRepository.findById(anyLong())).thenReturn(Optional.of(rule));

        var ruleRetorned = calculateService.findRuleById(1L);


        verify(calculateRepository, times(2)).findById(anyLong());

        assertEquals(rule.getId(), ruleRetorned.getId());
        assertEquals(rule.getParity(), ruleRetorned.getParity());
        assertEquals(rule.getCategory(), ruleRetorned.getCategory());



    }

    @Test
    void findRuleByIdNotValid(){
        when(calculateRepository.findById(anyLong())).thenReturn(Optional.empty());


        Long ruleId = 1L;
        RuleResponseDto response = calculateService.findRuleById(ruleId);


        assertEquals(ruleId, response.getId());
        assertEquals("without category", response.getCategory());
        assertEquals(1, response.getParity());

    }

    @Test
    void getAllRules(){
        when(calculateRepository.findAll()).thenReturn(List.of(rule));

        List<Rule> ruleList = calculateService.getAllRules();

        verify(calculateRepository,times( 1)).findAll();


    }

    @Test
    void deleteRuleByIdValid(){
        when(calculateRepository.findById(anyLong())).thenReturn(Optional.of(rule));
        doNothing().when(calculateRepository).delete(any(Rule.class));

       calculateService.deleteRuleById(1L);

       verify(calculateRepository, times(1)).findById(anyLong());
        verify(calculateRepository, times(1)).delete(any(Rule.class));





    }

    @Test
    void deleteRuleByIdNotValid(){
        when(calculateRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(RuleNotFoundException.class, () ->{
            calculateService.deleteRuleById(1L);
        });

        verify(calculateRepository, times(1)).findById(anyLong());
        verify(calculateRepository, times(0)).delete(any(Rule.class));

    }

    @Test
    void updateRuleByIdValid(){
        when(calculateRepository.findById(anyLong())).thenReturn(Optional.of(rule));
        when(calculateRepository.save(any(Rule.class))).thenReturn(rule);


        var ruleUpdated = calculateService.updateCalculateById(ruleCreateUpdateDto,1L);

        verify(calculateRepository, times(1)).save(any(Rule.class));
        verify(calculateRepository,times(1)).findById(anyLong());

        assertEquals(rule.getId(), ruleUpdated.getId());
        assertEquals(ruleUpdated.getParity(), 9);//updated
        assertEquals(rule.getCategory(), ruleUpdated.getCategory());

    }

    @Test
    void updateRuleByIdNotValid(){
        when(calculateRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(RuleNotFoundException.class, () ->{
            calculateService.updateCalculateById(ruleCreateUpdateDto,1L);
        });


        verify(calculateRepository,times(1)).findById(anyLong());
        verify(calculateRepository,times(0)).save(any(Rule.class));

    }

    @Test
    void calculateValueWithIdValid(){
        when(calculateRepository.findById(anyLong())).thenReturn(Optional.of(rule));

       var calculate =  calculateService.calculateValue(calculateDto);

        verify(calculateRepository, times(2)).findById(2L);


        assertEquals(calculate.getTotal(), 20000);

    }

    @Test
    void calculateValueWithIdNotValid(){
        when(calculateRepository.findById(anyLong())).thenReturn(Optional.empty());

        var calculate =  calculateService.calculateValue(calculateDto);

        verify(calculateRepository, times(1)).findById(2L);


        assertEquals(calculate.getTotal(), 2000);

    }







}
