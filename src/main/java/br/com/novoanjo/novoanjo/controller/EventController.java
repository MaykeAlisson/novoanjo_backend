package br.com.novoanjo.novoanjo.controller;

import br.com.novoanjo.novoanjo.domain.commons.dto.EventInfoDto;
import br.com.novoanjo.novoanjo.domain.commons.dto.EventRequestDto;
import br.com.novoanjo.novoanjo.domain.model.Event;
import br.com.novoanjo.novoanjo.infra.exception.BussinesException;
import br.com.novoanjo.novoanjo.service.event.EventService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Objects;
import java.util.Set;

import static br.com.novoanjo.novoanjo.infra.util.jwt.Token.getUserId;

@Slf4j
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

        log.info("EventController.create - start - EventRequestDto {}", obj);
        final Event event = eventService.create(obj, 6L);
        final URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(event.getId()).toUri();
        log.info("EventController.create - end - Event.id {}", event.getId());
        return ResponseEntity.created(uri).build();

    }

    @GetMapping(value = "/v1/event/{id}")
    @ApiOperation(
            value = "Esta operação busca um evento por id",
            notes = "O evento pode existir mais so sera retornado se ja foi aprovado"
    )
    public ResponseEntity<EventInfoDto> findById(@PathVariable final Long id) {

        log.info("EventController.findById - start - idEvent {}", id);
        if (Objects.isNull(id)) {
            throw new BussinesException("id required!");
        }

        final EventInfoDto event = eventService.findById(id);

        if (Objects.isNull(event)) {
            return ResponseEntity.noContent().build();
        }
        log.info("EventController.findById - end - EventInfoDto {}", event);
        return ResponseEntity.ok().body(event);

    }

    @GetMapping(value = "/v1/event")
    @ApiOperation(
            value = "Esta operação criar um novo evento no sistema",
            notes = ""
    )
    public ResponseEntity<Set<EventInfoDto>> findByAllApproved() {

        log.info("EventController.findByAllApproved - start ");
        final Set<EventInfoDto> allApproved = eventService.findAllApproved();
        log.info("EventController.findByAllApproved - end - EventInfoDto {}", allApproved);
        return ResponseEntity.ok().body(allApproved);

    }

    @GetMapping(value = "/v1/event/state/{state}")
    @ApiOperation(
            value = "Esta operação criar um novo evento no sistema",
            notes = ""
    )
    public ResponseEntity<Set<EventInfoDto>> findByState(@PathVariable final String state) {

        log.info("EventController.findByState - start - State {}", state);
        if (StringUtils.isBlank(state)) {
            throw new BussinesException("state required!");
        }

        final Set<EventInfoDto> eventState = eventService.findByState(state);

        log.info("EventController.findByState - end - State {}", eventState);
        return ResponseEntity.ok(eventState);

    }

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

    //    @PutMapping(value = "/v1/event/pendent")
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
