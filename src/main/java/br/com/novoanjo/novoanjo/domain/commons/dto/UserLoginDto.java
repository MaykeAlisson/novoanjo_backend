package br.com.novoanjo.novoanjo.domain.commons.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginDto implements Serializable {

    @NotNull
    @Email
    private String email;
    @NotNull @Length(min = 6)
    private String senha;


    public static UsernamePasswordAuthenticationToken convert(final UserLoginDto dto) {
        return new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getSenha());
    }

}
