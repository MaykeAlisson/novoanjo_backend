package br.com.novoanjo.novoanjo.controller;

import br.com.novoanjo.novoanjo.domain.commons.constante.ProfileName;
import br.com.novoanjo.novoanjo.domain.commons.dto.*;
import br.com.novoanjo.novoanjo.infra.exception.BussinesException;
import br.com.novoanjo.novoanjo.service.user.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Objects;
import java.util.Set;

import static br.com.novoanjo.novoanjo.domain.commons.constante.ProfileName.existValue;
import static br.com.novoanjo.novoanjo.infra.util.jwt.Token.getUserId;
import static br.com.novoanjo.novoanjo.infra.util.jwt.Token.getUserPerfil;
import static java.lang.Boolean.FALSE;
import static org.apache.commons.lang3.StringUtils.isBlank;

@RestController
@RequestMapping(value = "/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "/v1/user")
    @ApiOperation(
            value = "Esta operação criar um novo usuario no sistema",
            notes = ""
    )
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
    public ResponseEntity<Set<UserInfoDto>> findByProfile(@PathVariable final String profile) {

        if (isBlank(profile)) {
            throw new BussinesException("profile required!");
        }

        if (FALSE.equals(existValue(profile))) {
            throw new BussinesException("profile value A or S");
        }

        if (!Objects.equals(getUserPerfil(), ProfileName.M.getValor())) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok().body(userService.findAllByProfile(ProfileName.valueOf(profile)));
    }

    @GetMapping(value = "/v1/user/service/{id}")
    public ResponseEntity<Set<UserInfoDto>> findByService(@PathVariable final Long id) {

        if (Objects.isNull(id)) {
            throw new BussinesException("id required!");
        }

        if (getUserPerfil().contains(ProfileName.S.getValor())) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok().body(userService.findAllByService(id));
    }

    @GetMapping(value = "/v1/user/discovery")
    public ResponseEntity<Set<UserInfoDto>> findByDiscovery() {

        if (getUserPerfil().contains(ProfileName.S.getValor())) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok().body(userService.userDiscoverService(getUserId()));
    }

    @PostMapping(value = "/v1/user/service")
    @ApiOperation(
            value = "Esta operação criar um novo usuario no sistema",
            notes = ""
    )
    public ResponseEntity<Void> userToService(@RequestBody @Valid final UserToServiceDto obj) {

        userService.userToService(obj, getUserId());

        return ResponseEntity.ok().build();

    }

    @DeleteMapping(value = "/v1/user/service")
    @ApiOperation(
            value = "Esta operação criar um novo usuario no sistema",
            notes = ""
    )
    public ResponseEntity<Void> userRemoveService(@RequestBody @Valid final UserToServiceDto obj) {

        userService.userRemoveService(obj, getUserId());

        return ResponseEntity.ok().build();

    }

}
