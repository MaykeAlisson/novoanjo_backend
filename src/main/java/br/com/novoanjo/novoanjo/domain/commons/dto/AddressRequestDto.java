package br.com.novoanjo.novoanjo.domain.commons.dto;

import br.com.novoanjo.novoanjo.domain.model.Address;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddressRequestDto implements Serializable {

    @NotBlank
    private String zipCode;

    @NotBlank
    private String logradouro;

    private String complement;

    @NotNull
    private Long number;

    @NotBlank
    private String city;

    @NotBlank
    private String state;

    public static AddressRequestDto toAddress(Address address){
        return AddressRequestDto.builder()
                .zipCode(address.getZipCode())
                .logradouro(address.getLogradouro())
                .complement(address.getComplement())
                .number(address.getNumber())
                .city(address.getCity())
                .state(address.getState())
                .build();
    }
}
