package br.com.novoanjo.novoanjo.service.event;

import br.com.novoanjo.novoanjo.domain.commons.dto.EventInfoDto;
import br.com.novoanjo.novoanjo.domain.commons.dto.EventRequestDto;
import br.com.novoanjo.novoanjo.domain.model.Event;

import java.util.Set;

public interface EventService {

    Event create(EventRequestDto request, Long idUser);

    EventInfoDto findById(Long idEvent);

    Set<EventInfoDto> findAllApproved();

    Set<EventInfoDto> findByState(String state);

    Set<EventInfoDto> findAllPendent();

    void update(EventRequestDto dto, Long idEvent, Long idUser);
}
