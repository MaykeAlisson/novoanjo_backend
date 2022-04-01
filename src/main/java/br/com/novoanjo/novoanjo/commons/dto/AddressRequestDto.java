package br.com.novoanjo.novoanjo.commons.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddressRequestDto implements Serializable {

    @NotNull
    private String zipCode;

    @NotNull
    private String logradouro;

    private String complement;

    @NotNull
    private Long number;

    @NotNull
    private String state;
}
