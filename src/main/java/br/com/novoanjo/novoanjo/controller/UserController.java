package br.com.novoanjo.novoanjo.controller;

import br.com.novoanjo.novoanjo.domain.commons.constante.ProfileName;
import br.com.novoanjo.novoanjo.domain.commons.dto.UserAccessDto;
import br.com.novoanjo.novoanjo.domain.commons.dto.UserByProfileDto;
import br.com.novoanjo.novoanjo.domain.commons.dto.UserRequestDto;
import br.com.novoanjo.novoanjo.domain.commons.dto.UserRequestUpdateDto;
import br.com.novoanjo.novoanjo.infra.exception.BussinesException;
import br.com.novoanjo.novoanjo.infra.util.jwt.Token;
import br.com.novoanjo.novoanjo.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Arrays;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static br.com.novoanjo.novoanjo.domain.commons.constante.ProfileName.existValue;
import static br.com.novoanjo.novoanjo.infra.util.jwt.Token.getUserId;
import static org.apache.commons.lang3.StringUtils.isBlank;

@RestController
@RequestMapping(value = "/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "/v1/user")
    public ResponseEntity<UserAccessDto> create(@RequestBody @Valid final UserRequestDto obj) {

        final UserAccessDto user = userService.createUser(obj);

        final URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(user.getId()).toUri();
        return ResponseEntity.created(uri).body(user);

    }

    @PutMapping(value = "/v1/user")
    public ResponseEntity<Void> update(@RequestBody @Valid final UserRequestUpdateDto obj) {

        userService.updateUser(obj, getUserId());

        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/v1/user/profile/{profile}")
    public ResponseEntity<Set<UserByProfileDto>> findByProfile(@PathVariable final String profile) {

        if (isBlank(profile)) {
            throw new BussinesException("profile required!");
        }

        if(!existValue(profile)){
            throw new BussinesException("profile value A or S");
        }

//        if(!Objects.equals(Token.getUserPerfil(), ProfileName.M.getValor())){
//            return ResponseEntity.noContent().build();
//        }

        final Set<UserByProfileDto> users = userService.findAllByProfile(ProfileName.valueOf(profile));

        return ResponseEntity.ok().build();
    }

//    @GetMapping(value = "/v1/user/service/{id}")
//    public ResponseEntity<Void> findByProfile(@PathVariable final String id) {
//        // busca todos usuarios com o perfil solicitante para o serviço selecionado
//        // TODO - Somente para user profile A and M
//    }

//    @GetMapping(value = "/v1/user/discovery")
//    public ResponseEntity<Void> findByDiscovery() {
//        // busca todos usuarios com o perfil s onde o serviço e o mesmo do requerente
//        // TODO - Somente para user profile A and M
//    }
}
