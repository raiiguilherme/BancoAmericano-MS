package org.grupo5.mscalculate.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "rule_bd")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Rule {

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    @Column(length = 50, nullable = false, unique = true)
    private String category;
    @Column(nullable = false)
    private Integer parity;




}
