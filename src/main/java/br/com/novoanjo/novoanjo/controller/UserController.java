package br.com.novoanjo.novoanjo.controller;

import br.com.novoanjo.novoanjo.config.exception.BussinesException;
import br.com.novoanjo.novoanjo.domain.commons.constante.ProfileName;
import br.com.novoanjo.novoanjo.domain.commons.dto.*;
import br.com.novoanjo.novoanjo.service.user.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Objects;
import java.util.Set;

import static br.com.novoanjo.novoanjo.config.jwt.Token.getUserId;
import static br.com.novoanjo.novoanjo.config.jwt.Token.getUserPerfil;
import static br.com.novoanjo.novoanjo.domain.commons.constante.ProfileName.existValue;
import static java.lang.Boolean.FALSE;
import static org.apache.commons.lang3.StringUtils.isBlank;

@Slf4j
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
    public ResponseEntity<UserAccessDto> create(@Valid @RequestBody final UserRequestDto obj) {

        log.info("UserController.create - start - UserRequestDto {}", obj);
        final UserAccessDto user = userService.createUser(obj);
        final URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(user.getId()).toUri();
        log.info("UserController.create - end - UserAccessDto {}", user);
        return ResponseEntity.created(uri).body(user);

    }

    @PutMapping(value = "/v1/user")
    @ApiOperation(
            value = "Esta operação atualiza dados do usuario no sistema",
            notes = ""
    )
    public ResponseEntity<Void> update(@Valid @RequestBody final UserRequestUpdateDto obj) {

        log.info("UserController.update - start - UserRequestUpdateDto {}", obj);
        userService.updateUser(obj, getUserId());
        log.info("UserController.update - end ");
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/v1/user/profile/{profile}")
    @ApiOperation(
            value = "Esta operação busca todos os usuarios por perfil",
            notes = "Somente usuario master pode realizar esta consulta"
    )
    public ResponseEntity<Set<UserInfoDto>> findByProfile(@PathVariable final String profile) {

        log.info("UserController.findByProfile - start - Profile {}", profile);
        if (isBlank(profile)) {
            throw new BussinesException("profile obrigatorio!");
        }

        if (FALSE.equals(existValue(profile))) {
            throw new BussinesException("profile valores A or S");
        }

        if (!Objects.equals(getUserPerfil(), ProfileName.M.getValor())) {
            return ResponseEntity.noContent().build();
        }

        final Set<UserInfoDto> users = userService.findAllByProfile(ProfileName.valueOf(profile));
        log.info("UserController.findByProfile - end - UserInfoDto {}", users);

        return ResponseEntity.ok().body(users);
    }

    @GetMapping(value = "/v1/user/service/{id}")
    @ApiOperation(
            value = "Esta operação busca usuarios por servico",
            notes = "Busca usuarios onde o perfil e S e o servico e o informado"
    )
    public ResponseEntity<Set<UserInfoDto>> findByService(@PathVariable final Long id) {

        log.info("UserController.findByService - start - id {}", id);
        if (Objects.isNull(id)) {
            throw new BussinesException("id obrigatorio!");
        }

        if (getUserPerfil().contains(ProfileName.S.getValor())) {
            return ResponseEntity.noContent().build();
        }

        Set<UserInfoDto> users = userService.findAllByService(id);

        log.info("UserController.findByService - end - UserInfoDto {}", users);

        return ResponseEntity.ok().body(users);
    }

    @GetMapping(value = "/v1/user/discovery")
    @ApiOperation(
            value = "Esta operação busca todos os usuarios com os mesmos servicos e perfil S",
            notes = "So pode ser usado por perfil A e M"
    )
    public ResponseEntity<Set<UserInfoDto>> findByDiscovery() {

        log.info("UserController.findByDiscovery - start - UserInfoDto ");

        if (getUserPerfil().contains(ProfileName.S.getValor())) {
            return ResponseEntity.noContent().build();
        }

        final Set<UserInfoDto> userInfoDtos = userService.userDiscoverService(getUserId());

        log.info("UserController.findByDiscovery - end - UserInfoDto {}", userInfoDtos);

        return ResponseEntity.ok().body(userInfoDtos);
    }

    @PostMapping(value = "/v1/user/service")
    @ApiOperation(
            value = "Esta operação criar novos servicos para usuario",
            notes = ""
    )
    public ResponseEntity<Void> userToService(@Valid @RequestBody final UserToServiceDto obj) {

        log.info("UserController.userToService - start - UserToServiceDto {}", obj);

        userService.userToService(obj, getUserId());

        log.info("UserController.userToService - end ");

        return ResponseEntity.ok().build();

    }

    @DeleteMapping(value = "/v1/user/service")
    @ApiOperation(
            value = "Esta operação deleta servicos do usuario",
            notes = ""
    )
    public ResponseEntity<Void> userRemoveService(@Valid @RequestBody final UserToServiceDto obj) {

        log.info("UserController.userRemoveService - start - UserToServiceDto {}", obj);

        userService.userRemoveService(obj, getUserId());

        log.info("UserController.userRemoveService - end ");

        return ResponseEntity.ok().build();

    }

}
