package br.com.novoanjo.novoanjo.controller;

import br.com.novoanjo.novoanjo.domain.commons.dto.EventInfoDto;
import br.com.novoanjo.novoanjo.domain.commons.dto.EventRequestDto;
import br.com.novoanjo.novoanjo.domain.model.Event;
import br.com.novoanjo.novoanjo.infra.exception.BussinesException;
import br.com.novoanjo.novoanjo.service.event.EventService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Objects;
import java.util.Set;

import static br.com.novoanjo.novoanjo.infra.util.jwt.Token.getUserId;

@RestController
@RequestMapping(value = "/api/event")
public class EventController {

    @Autowired
    private EventService eventService;

    @PostMapping(value = "/v1/event")
    @ApiOperation(
            value = "Esta operação criar um novo evento no sistema",
            notes = ""
    )
    public ResponseEntity<EventInfoDto> create(@Valid @RequestBody final EventRequestDto obj) {

        final Event event = eventService.create(obj, 6L);

        final URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(event.getId()).toUri();
        return ResponseEntity.created(uri).build();

    }

    @GetMapping(value = "/v1/event/{id}")
    @ApiOperation(
            value = "Esta operação criar um novo evento no sistema",
            notes = ""
    )
    public ResponseEntity<EventInfoDto> findById(@PathVariable final Long id) {

        if (Objects.isNull(id)) {
            throw new BussinesException("id required!");
        }

        final EventInfoDto event = eventService.findById(id);

        if(Objects.isNull(event)){
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok().body(event);

    }

//    @GetMapping(value = "/v1/event")
//    @ApiOperation(
//            value = "Esta operação criar um novo evento no sistema",
//            notes = ""
//    )
//    public ResponseEntity<Set<EventInfoDto>> findByAll() {
//
//        final URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(event.getId()).toUri();
//        return ResponseEntity.created(uri).build();
//
//    }

//    @GetMapping(value = "/v1/event/state/{state}")
//    @ApiOperation(
//            value = "Esta operação criar um novo evento no sistema",
//            notes = ""
//    )
//    public ResponseEntity<Set<EventInfoDto>> findByState(@Valid @RequestBody final EventRequestDto obj) {
//
//        final Event event = eventService.create(obj, getUserId());
//
//        final URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(event.getId()).toUri();
//        return ResponseEntity.created(uri).build();
//
//    }

//    @PutMapping(value = "/v1/event")
//    @ApiOperation(
//            value = "Esta operação criar um novo evento no sistema",
//            notes = ""
//    )
//    public ResponseEntity<Set<EventInfoDto>> update(@Valid @RequestBody final EventRequestDto obj) {
//
//        final Event event = eventService.create(obj, getUserId());
//
//        final URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(event.getId()).toUri();
//        return ResponseEntity.created(uri).build();
//
//    }
}
