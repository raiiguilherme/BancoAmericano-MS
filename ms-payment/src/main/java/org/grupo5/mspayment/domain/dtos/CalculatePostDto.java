package org.grupo5.mspayment.domain.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CalculatePostDto {

    private Long customerId;
    private Long categoryId;
    private Integer total;

}
