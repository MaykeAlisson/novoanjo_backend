package br.com.novoanjo.novoanjo.service.event;

import br.com.novoanjo.novoanjo.domain.commons.dto.EventRequestDto;
import br.com.novoanjo.novoanjo.domain.model.Event;

public interface EventService {

    Event create(EventRequestDto request, Long idUser);

}
