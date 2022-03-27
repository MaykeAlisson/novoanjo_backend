package br.com.novoanjo.novoanjo.commons.dto;

import br.com.novoanjo.novoanjo.commons.constant.ProfileName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDto implements Serializable {

    @NotNull
    private String name;

    @NotNull
    private LocalDateTime birth;

    @NotNull
    private String email;

    @NotNull
    private String password;

    @NotNull
    private ProfileName profileName;

    @NotNull
    private PhoneRequestDto phone;

    @NotNull
    private AddressRequestDto address;
}
