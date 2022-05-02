package br.com.novoanjo.novoanjo.service.event.impl;

import br.com.novoanjo.novoanjo.domain.commons.constante.Approved;
import br.com.novoanjo.novoanjo.domain.commons.constante.ProfileName;
import br.com.novoanjo.novoanjo.domain.commons.dto.EventApproved;
import br.com.novoanjo.novoanjo.domain.commons.dto.EventInfoDto;
import br.com.novoanjo.novoanjo.domain.commons.dto.EventRequestDto;
import br.com.novoanjo.novoanjo.domain.model.Event;
import br.com.novoanjo.novoanjo.domain.model.Profile;
import br.com.novoanjo.novoanjo.domain.model.User;
import br.com.novoanjo.novoanjo.infra.exception.BussinesException;
import br.com.novoanjo.novoanjo.infra.exception.NotFoundException;
import br.com.novoanjo.novoanjo.repository.EventRepository;
import br.com.novoanjo.novoanjo.repository.ProfileRepository;
import br.com.novoanjo.novoanjo.repository.UserRepository;
import br.com.novoanjo.novoanjo.service.event.EventService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static br.com.novoanjo.novoanjo.domain.commons.dto.EventInfoDto.toEventInfo;
import static br.com.novoanjo.novoanjo.domain.commons.dto.EventInfoDto.toListEventInfoDto;
import static br.com.novoanjo.novoanjo.domain.model.Event.toEvent;
import static br.com.novoanjo.novoanjo.domain.model.Event.toEventUpdate;
import static java.lang.String.format;

@Slf4j
@Service
public class EventServiceImpl implements EventService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProfileRepository profileRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public Event create(final EventRequestDto request, final Long idUser){

        log.info("EventServiceImpl.create - start - EventRequestDto {} - idUser {}", request, idUser);

        User user = userRepository.findById(idUser)
                .orElseThrow(() -> new NotFoundException(format("Não foi encontrado usuario com o id %s", idUser)));

        log.info("EventServiceImpl.create - end ");
        return eventRepository.save(toEvent(request, user));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EventInfoDto findById(final Long idEvent){

        log.info("EventServiceImpl.findById - start - idEvent {}", idEvent);
        final Event event = eventRepository.findById(idEvent)
                .orElseThrow(() -> new NotFoundException(format("Não foi encontrado evento com id %s", idEvent)));

        log.info("EventServiceImpl.findById - end - Event {}", event);
        return Objects.equals(event.getApproved(), Approved.S)
                ? toEventInfo(event)
                : null;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<EventInfoDto> findAllApproved(){

        log.info("EventServiceImpl.findAll - start ");

        Set<Event> events = eventRepository.findByApprovedAndDataAfter(Approved.S, LocalDateTime.now());

        log.info("EventServiceImpl.findAll - end - Event.size {}", events.size());

        return toListEventInfoDto(events);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<EventInfoDto> findByState(final String state){

        log.info("EventServiceImpl.findByState - start - State {}", state);

        Set<Event> events = eventRepository.findByState(state, LocalDateTime.now());

        log.info("EventServiceImpl.findByState - end - Event.size {}", events.size());

        return toListEventInfoDto(events);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<EventInfoDto> findAllPendent(){

        log.info("EventServiceImpl.findAllPendent - start ");

        Set<Event> events = eventRepository.findByApprovedAndDataAfter(Approved.N, LocalDateTime.now());

        log.info("EventServiceImpl.findAllPendent - end - Event.size {}", events.size());

        return toListEventInfoDto(events);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(final EventRequestDto dto, final Long idEvent, final Long idUser){

        log.info("EventServiceImpl.update - start - EventRequestDto {} - idEvent {} - idUser {}",dto, idEvent, idUser);

        Event event = eventRepository.findById(idEvent)
                .orElseThrow(() -> new NotFoundException("Não foi encontrado evento com id " + idEvent));

        if (!event.getUser().getId().equals(idUser))
            throw new BussinesException(format("Usuario com o id %s não foi o criador do evento", idUser));

        eventRepository.save(toEventUpdate(dto, event));
        
        log.info("EventServiceImpl.update - end ");

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void aprove(final EventApproved ids){

        log.info("EventServiceImpl.aprove - start - EventApproved {} ", ids);
        Set<Event> events = ids.getEvents().stream().map(id -> eventRepository.findById(id)
                        .orElseThrow(() -> new NotFoundException(format("Não foi encontrado evento com o id %s", id))))
                .collect(Collectors.toSet());

        events.forEach(event -> event.setApproved(Approved.S));
        eventRepository.saveAll(events);

        log.info("EventServiceImpl.aprove - end ");

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteById(final Long id, final Long idUser){

        log.info("EventServiceImpl.deleteById - start - idEvent {} - idUser {}",id, idUser);

        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Não foi localizado evento com o id " + id));

        if(!event.getUser().getId().equals(idUser))
            throw new BussinesException(format("Este evento não foi criado pelo usuario com id %s", idUser));

        eventRepository.delete(event);

        log.info("EventServiceImpl.deleteById - end ");

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void verificaEventosPendente(){

        log.info("EventServiceImpl.verificaEventosPendente - start ");

        Set<Event> events = eventRepository.findByApprovedAndDataAfter(Approved.N, LocalDateTime.now());

        if (events.isEmpty()) return;

        final Profile profile = profileRepository.findByProfileName(ProfileName.M)
                .orElseThrow(() -> new NotFoundException(format("Não foi encontrado perfil com o nome %s", ProfileName.M)));
        Set<User> users = userRepository.findByProfile(profile);

        users.forEach(user -> mandarEmailEventoPendente(user, events));

        log.info("EventServiceImpl.verificaEventosPendente - end ");
    }

    private void mandarEmailEventoPendente(final User user, final Set<Event> events){

        final String email = user.getEmail();

        final String corpoEmail = criarCorpoEmail(user.getName(), events);

        System.out.println(format("Enviando email para %s - corpo %s", email, corpoEmail));

    }

    private String criarCorpoEmail(final String destinatario, final Set<Event> events){
       StringBuffer corpo = new StringBuffer();
        corpo.append(format("Olá Anjo %s \n", destinatario));
        corpo.append(format("existe um total de %s eventos pendentes de aprovação, segue os eventos abaixo. \n", events.size()));

        events.forEach(event -> {
            corpo.append(format("Nome: %s \n", event.getName()));
            corpo.append(format("Descrição: %s \n", event.getDescription()));
            corpo.append(format("Data: %s \n", event.getData()));
            corpo.append(format("Data-Cadastro: %s \n", event.getDataCadastro()));
        });

        return String.valueOf(corpo);
    }
}
