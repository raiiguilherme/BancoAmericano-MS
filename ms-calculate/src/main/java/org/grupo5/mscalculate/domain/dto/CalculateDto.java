package org.grupo5.mscalculate.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CalculateDto {

    @NotNull
    private Long categoryId;
    @NotNull
    private Long customerId;
    @NotNull
    private Integer total;
}
