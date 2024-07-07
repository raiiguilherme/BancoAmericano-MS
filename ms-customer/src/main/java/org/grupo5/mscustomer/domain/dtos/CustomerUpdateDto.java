package org.grupo5.mscustomer.domain.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerUpdateDto {
    @NotBlank
    @Pattern(regexp = "\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}", message = "the CPF must follow this pattern xxx.xxx.xxx-xx")
    private String cpf;

    @NotBlank
    @Size(min = 3, max = 20)
    private String name;
    @NotBlank
    @Pattern(regexp = "Masculino|Feminino", message = "The gender must be 'Masculino' or 'Feminino'")
    private String gender;

    @JsonFormat(pattern = "dd/MM/yyyy") //formatando a data
    private LocalDate birthday; //maior de 18
    @NotBlank
    @Email(message = "Email in invalid format", regexp = "^[a-z0-9.+-]+@[a-z0-9.-]+\\.[a-z]{2,}$")
    private String email;
    
}
