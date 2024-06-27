package org.grupo5.mscustomer.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "customer")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 14, nullable = false, unique = true)
    private String cpf;
    @Column(length = 100, nullable = false)
    private String name;
    @Column(length = 10, nullable = false)
    private String gender;
    @Column(length = 10, nullable = false)
    @JsonFormat(pattern = "dd/MM/yyyy") //formatando a data
    private LocalDate birthday; //maior de 18

    @Column(length = 50, nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private Long points;
    @Column(nullable = false)
    private  String url_photo;





}
