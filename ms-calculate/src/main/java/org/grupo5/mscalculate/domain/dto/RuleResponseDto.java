package org.grupo5.mscalculate.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.grupo5.mscalculate.domain.Rule;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RuleResponseDto {

    private Long id;
    private String category;
    private Integer parity;


    public RuleResponseDto(Rule rule){
        this.id = rule.getId();
        this.category = rule.getCategory();
        this.parity = rule.getParity();
    }
}
