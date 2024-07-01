package org.grupo5.mscalculate.domain.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerMessageDto {
    @NotNull
    private Long customerId;
    @NotNull
    private Integer points;
}
