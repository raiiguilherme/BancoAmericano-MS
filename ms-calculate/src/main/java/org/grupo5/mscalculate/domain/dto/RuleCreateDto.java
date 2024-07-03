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
public class RuleCreateDto {
    @NotBlank(message = "Category not be blank")
    private String category;
    @NotNull(message = "Parity not be blank")
    private Integer parity;
}
