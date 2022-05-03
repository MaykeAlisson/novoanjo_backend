package br.com.novoanjo.controller;

import br.com.novoanjo.config.exception.BussinesException;
import br.com.novoanjo.config.jwt.Token;
import br.com.novoanjo.domain.commons.dto.UserAccessDto;
import br.com.novoanjo.domain.commons.dto.UserLoginDto;
import br.com.novoanjo.domain.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/access")
public class AccessController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping(value = "/v1/login")
    public ResponseEntity<UserAccessDto> create(@RequestBody @Valid final UserLoginDto dto) {
        try {
            UsernamePasswordAuthenticationToken dadosLogin = UserLoginDto.convert(dto);
            Authentication authenticate = authenticationManager.authenticate(dadosLogin);
            final User user = (User) authenticate.getPrincipal();
            final String token = Token.gerar(user.getId(), user.getProfile().getProfileName().getValor())
                    .orElseThrow(() -> new BussinesException("Erro ao Gerar token!"));

            final UserAccessDto userAccess = UserAccessDto.builder()
                    .id(user.getId())
                    .name(user.getName())
                    .profile(user.getProfile().getProfileName().getValor())
                    .token(token)
                    .build();

            return ResponseEntity.ok().body(userAccess);
        } catch (Exception e) {
            throw new BussinesException("Dados de Login Invalidos! " + e.getMessage());
        }

    }
}
