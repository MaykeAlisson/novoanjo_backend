package br.com.novoanjo.novoanjo.service.event.impl;

import br.com.novoanjo.novoanjo.domain.commons.constante.Approved;
import br.com.novoanjo.novoanjo.domain.commons.dto.EventInfoDto;
import br.com.novoanjo.novoanjo.domain.commons.dto.EventRequestDto;
import br.com.novoanjo.novoanjo.domain.model.Event;
import br.com.novoanjo.novoanjo.domain.model.User;
import br.com.novoanjo.novoanjo.infra.exception.NotFoundException;
import br.com.novoanjo.novoanjo.repository.EventRepository;
import br.com.novoanjo.novoanjo.repository.UserRepository;
import br.com.novoanjo.novoanjo.service.event.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static br.com.novoanjo.novoanjo.domain.commons.dto.EventInfoDto.toEventInfo;
import static br.com.novoanjo.novoanjo.domain.model.Event.toEvent;
import static java.lang.String.format;

@Service
public class EventServiceImpl implements EventService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Event create(final EventRequestDto request, final Long idUser){

        User user = userRepository.findById(idUser)
                .orElseThrow(() -> new NotFoundException(format("not found user with id %s", idUser)));

        return eventRepository.save(toEvent(request, user));
    }

    @Override
    public EventInfoDto findById(final Long idEvent){

        final Event event = eventRepository.findById(idEvent)
                .orElseThrow(() -> new NotFoundException(format("not found event with id %s", idEvent)));

        return Objects.equals(event.getApproved(), Approved.S)
                ? toEventInfo(event)
                : null;

    }
}
