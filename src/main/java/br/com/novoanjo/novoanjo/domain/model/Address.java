package br.com.novoanjo.novoanjo.domain.model;

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
    private String logradouro;
    private String complement;
    private Long number;
    private String state;

}
