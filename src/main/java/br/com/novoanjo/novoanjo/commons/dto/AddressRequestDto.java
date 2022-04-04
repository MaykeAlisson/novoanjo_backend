package br.com.novoanjo.novoanjo.commons.dto;

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
    private String state;
}
