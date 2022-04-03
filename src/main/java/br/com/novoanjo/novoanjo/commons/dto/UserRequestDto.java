package br.com.novoanjo.novoanjo.commons.dto;

import br.com.novoanjo.novoanjo.commons.constante.ProfileName;
import br.com.novoanjo.novoanjo.commons.json.ProfileNameDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDto implements Serializable {

    @NotNull
    private String name;

    @NotNull
    private LocalDate birth;

    @NotNull
    private String email;

    @NotNull
    private String password;

    @NotNull
    @JsonDeserialize(using = ProfileNameDeserializer.class)
    private ProfileName profileName;

    @NotNull
    private PhoneRequestDto phone;

    @NotNull
    private AddressRequestDto address;
}
