package org.grupo5.mspayment.domain.dtos;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentCreateDto {



    @NotNull
    private Long customerId;
    @NotNull
    private Long categoryId;
    @NotNull
    private Integer total;

}
