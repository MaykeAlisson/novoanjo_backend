package br.com.novoanjo.novoanjo.domain;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "address")
public class Address implements Serializable {

    @EqualsAndHashCode.Include
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "zip_code", nullable = false, length = 3)
    private String zipCode;

    @Column(name = "logradouro", nullable = false, length = 130)
    private String logradouro;

    @Column(name = "complement", nullable = true, length = 50)
    private String complement;

    @Column(name = "number", nullable = false)
    private Long number;

    @Column(name = "state", nullable = false, length = 2)
    private String state;

}
