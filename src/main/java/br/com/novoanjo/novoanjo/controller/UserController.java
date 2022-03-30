package br.com.novoanjo.novoanjo.controller;

import br.com.novoanjo.novoanjo.commons.dto.UserAccessDto;
import br.com.novoanjo.novoanjo.commons.dto.UserRequestDto;
import br.com.novoanjo.novoanjo.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/v1/user", method = RequestMethod.POST)
    public ResponseEntity<UserAccessDto> create(@RequestBody @Valid final UserRequestDto obj) {

        final UserAccessDto user = userService.createUser(obj);

        final URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(user.getId()).toUri();
        return ResponseEntity.created(uri).body(user);

    }
}
