package org.grupo5.mscustomer.domain.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.format.annotation.DateTimeFormat;


import java.time.LocalDate;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerCreateDto {

    @CPF
    private String cpf;
    @NotBlank
    private String name;
    @NotBlank
    private String gender;

    @JsonFormat(pattern = "dd/MM/yyyy") //formatando a data
    private LocalDate birthday; //maior de 18
    @NotBlank
    @Email(message = "Email em formato invalido", regexp = "^[a-z0-9.+-]+@[a-z0-9.-]+\\.[a-z]{2,}$")
    private String email;
    @NotBlank
    private  String url_photo;

}
