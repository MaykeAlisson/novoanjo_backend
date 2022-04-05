package br.com.novoanjo.novoanjo.domain.commons.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestUpdateDto implements Serializable {

    private Short ddd;
    private Long number;
    private String zipCode;
    private String logradouro;
    private String complement;
    private Long numberAddress;
    private String state;
}
