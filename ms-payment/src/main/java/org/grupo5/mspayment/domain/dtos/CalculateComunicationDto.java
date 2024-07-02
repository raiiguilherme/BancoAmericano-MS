package org.grupo5.mspayment.domain.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CalculateComunicationDto {

    private Long id;
    private String category;
    private Integer parity;
}
