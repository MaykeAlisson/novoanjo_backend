package br.com.novoanjo.novoanjo.domain.commons.dto;

import br.com.novoanjo.novoanjo.domain.commons.constante.ProfileName;
import br.com.novoanjo.novoanjo.domain.commons.json.ProfileNameDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDto implements Serializable {

    @NotBlank(message = "Name is mandatory")
    private String name;

    @NotNull
    private LocalDate birth;

    @NotBlank
    private String email;

    @NotBlank
    private String password;

    @NotNull(message = "profileName is mandatory value[A or S]")
    @JsonDeserialize(using = ProfileNameDeserializer.class)
    private ProfileName profileName;

    @NotNull
    private PhoneRequestDto phone;

    @NotNull
    private AddressRequestDto address;
}
