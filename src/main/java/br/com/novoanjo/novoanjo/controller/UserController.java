package br.com.novoanjo.novoanjo.controller;

import br.com.novoanjo.novoanjo.domain.dto.UserRequestDto;
import br.com.novoanjo.novoanjo.domain.dto.UsuarioAccessDto;
import br.com.novoanjo.novoanjo.domain.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping(value = "/api/user")
public class UserController {

    @RequestMapping(value = "/v1/user", method = RequestMethod.POST)
    public ResponseEntity<UsuarioAccessDto> create(@RequestBody @Valid final UserRequestDto obj) {

        final User user = userService.insert(obj);

        final String token = Token.gerar(user.getId(), 1L)
                .orElseThrow(() -> new BussinesException("Erro ao Gerar token!"));
        final UsuarioAccessDto usuarioAccessDto = new UsuarioAccessDto.Builder()
                .comIdUser(user.getId())
                .comNome(user.getNome())
                .comToken(token)
                .build();

        final URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(user.getId()).toUri();
        return ResponseEntity.created(uri).body(usuarioAcessoDto);

    }
}
