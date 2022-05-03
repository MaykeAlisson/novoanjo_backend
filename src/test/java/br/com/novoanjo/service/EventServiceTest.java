package br.com.novoanjo.service;

import br.com.novoanjo.BaseTest;
import br.com.novoanjo.domain.commons.constante.Approved;
import br.com.novoanjo.domain.commons.dto.EventApproved;
import br.com.novoanjo.domain.commons.dto.EventInfoDto;
import br.com.novoanjo.domain.commons.dto.EventRequestDto;
import br.com.novoanjo.domain.model.Event;
import br.com.novoanjo.repository.EventRepository;
import br.com.novoanjo.repository.UserRepository;
import br.com.novoanjo.service.event.EventService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.io.IOException;
import java.util.Collections;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class EventServiceTest extends BaseTest {

    @Autowired
    private EventService eventService;

    @MockBean
    private EventRepository eventRepository;

    @MockBean
    private UserRepository userRepository;

    @Captor
    private ArgumentCaptor<Event> eventCaptor;

    @Captor
    private ArgumentCaptor<Set<Event>> eventsCaptor;

    @Test
    void cadastrarEvento() throws IOException {

        EventRequestDto eventRequestDto = getEventRequestDto();

        when(userRepository.findById(any()))
                .thenReturn(Optional.of(getUser(1L)));

        eventService.create(eventRequestDto, any());

        verify(eventRepository).save(eventCaptor.capture());

        Event event = eventCaptor.getValue();

        Assertions.assertNotNull(event);
        Assertions.assertEquals(Approved.N, event.getApproved());

    }

    @Test
    void buscarPorIdQuandoNaoAprovado() {

        when(eventRepository.findById(any()))
                .thenReturn(Optional.of(getEvent()));

        EventInfoDto event = eventService.findById(any());

        Assertions.assertNull(event);

    }

    @Test
    void updateEvento() throws IOException {

        EventRequestDto eventRequestDto = getEventRequestDto();

        when(eventRepository.findById(any()))
                .thenReturn(Optional.of(getEvent()));

        eventService.update(eventRequestDto, anyLong(), 1L);

        verify(eventRepository).save(eventCaptor.capture());

        Event event = eventCaptor.getValue();

        Assertions.assertEquals(eventRequestDto.getName(), event.getName());
        Assertions.assertEquals(eventRequestDto.getDescription(), event.getDescription());
        Assertions.assertEquals(eventRequestDto.getData(), event.getData());

    }

    @Test
    void aprovarEvento() {

        when(eventRepository.findById(any()))
                .thenReturn(Optional.of(getEvent()));

        EventApproved build = EventApproved.builder()
                .events(Collections.singleton(1L))
                .build();

        eventService.aprove(build);

        verify(eventRepository).saveAll(eventsCaptor.capture());

        Set<Event> events = eventsCaptor.getValue();

        Set<Event> collect = events.stream()
                .filter(event -> Objects.equals(event.getApproved(), Approved.N))
                .collect(Collectors.toSet());

        Assertions.assertTrue(collect.isEmpty());
    }
}
