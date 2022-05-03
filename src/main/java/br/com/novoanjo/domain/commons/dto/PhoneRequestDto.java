package br.com.novoanjo.domain.commons.dto;

import br.com.novoanjo.domain.model.Phone;
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

    public static PhoneRequestDto toPhone(Phone phone){
        return PhoneRequestDto.builder()
                .ddd(phone.getDdd())
                .number(phone.getNumber())
                .build();
    }

}
