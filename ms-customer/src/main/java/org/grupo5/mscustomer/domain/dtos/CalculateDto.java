package org.grupo5.mscustomer.domain.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CalculateDto {
    private Long customerId;
    private Integer points;

}
