package br.com.novoanjo.domain.model;

import br.com.novoanjo.domain.commons.dto.AddressRequestDto;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "zip_code", nullable = false, length = 12)
    private String zipCode;

    @Column(name = "logradouro", nullable = false, length = 130)
    private String logradouro;

    @Column(name = "complement", nullable = true, length = 50)
    private String complement;

    @Column(name = "number", nullable = false)
    private Long number;

    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "state", nullable = false, length = 2)
    private String state;

    public static Address convertToAddress(final AddressRequestDto addressRequest){
        return Address.builder()
                .zipCode(addressRequest.getZipCode())
                .logradouro(addressRequest.getLogradouro())
                .complement(addressRequest.getComplement())
                .number(addressRequest.getNumber())
                .city(addressRequest.getCity())
                .state(addressRequest.getState())
                .build();
    }

}
