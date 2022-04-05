package br.com.novoanjo.novoanjo.domain.commons.dto;

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
public class PhoneRequestDto implements Serializable {

    @NotNull
    private Short ddd;

    @NotNull
    private Long number;

}
