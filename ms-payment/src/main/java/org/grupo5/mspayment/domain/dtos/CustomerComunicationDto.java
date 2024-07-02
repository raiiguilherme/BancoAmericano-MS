package org.grupo5.mspayment.domain.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerComunicationDto {

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
