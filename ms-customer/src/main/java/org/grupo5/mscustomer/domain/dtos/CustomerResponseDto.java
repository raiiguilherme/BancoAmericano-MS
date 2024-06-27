package org.grupo5.mscustomer.domain.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CustomerResponseDto {

    private Long id;
    private String cpf;

    private String name;

    private String gender;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate birthday;

    private String email;
    private Integer points;

    private  String url_photo;
}
