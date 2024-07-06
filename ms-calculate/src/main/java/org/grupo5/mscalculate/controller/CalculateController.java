package org.grupo5.mscalculate.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.grupo5.mscalculate.domain.Rule;
import org.grupo5.mscalculate.domain.dto.*;
import org.grupo5.mscalculate.exceptions.MessageError;
import org.grupo5.mscalculate.producer.CalculateProducer;
import org.grupo5.mscalculate.service.CalculateService;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class CalculateController {
    private final CalculateService calculateService;
    private final CalculateProducer calculateProducer;

    @Operation(summary = "create new rule")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "created with success.",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "422", description = "fields not valid",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageError.class)))
    })
    @PostMapping("/rules")
    public ResponseEntity<RuleResponseDto> postCalculate(@RequestBody @Valid RuleCreateDto ruleCreateDto){
        RuleResponseDto ruleResponseDto = new RuleResponseDto();
        var calculate = calculateService.createRule(ruleCreateDto);
        BeanUtils.copyProperties(calculate, ruleResponseDto);


        return ResponseEntity.ok(ruleResponseDto);

    }
    @Operation(summary = "get all rules")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "get with success.",
                    content = @Content(mediaType = "application/json"))
    })

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

    @Operation(summary = "delete rule by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "deleted with success.",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "rule not found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageError.class)))
    })
    @DeleteMapping("/rules/{id}")
    public ResponseEntity<String> deleteCalculateById(@PathVariable Long id){
        calculateService.deleteRuleById(id);
        return ResponseEntity.ok("Rule deleted successfully");
    }

    @Operation(summary = "update rule by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "updated with success.",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "rule not found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageError.class)))
    })
    @PutMapping("/rules/{id}")
    public ResponseEntity<RuleResponseDto> updateById(@PathVariable Long id, @RequestBody RuleCreateDto ruleCreateDto){
        RuleResponseDto ruleResponseDto = new RuleResponseDto();
       var calculate = calculateService.updateCalculateById(ruleCreateDto, id);
       BeanUtils.copyProperties(calculate, ruleResponseDto);
       return ResponseEntity.ok(ruleResponseDto);

    }


    @Operation(summary = "calculate send message for MSCustomer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "message send with success.",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "422", description = "invalid fields",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageError.class)))
    })
    @Transactional
    @PostMapping("/calculate")
    public ResponseEntity<CalculateResponseDto> PublishMessage(@RequestBody @Valid CalculateDto calculateDto){
           var calcule = calculateService.calculateValue(calculateDto);


            CustomerMessageDto customerMessageDto = new CustomerMessageDto();
            customerMessageDto.setCustomerId(calculateDto.getCustomerId());
            customerMessageDto.setPoints(calcule.getTotal());

            calculateProducer.pushMessageForCustomer(customerMessageDto);
           return ResponseEntity.ok(calcule);
    }
    @Operation(summary = "get rule by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "get with success.",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "rule not found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageError.class)))
    })

    @GetMapping("/rules/{id}")
    public ResponseEntity<RuleResponseDto> getRuleById(@PathVariable Long id){
        return ResponseEntity.ok(calculateService.findRuleById(id));
    }



}
