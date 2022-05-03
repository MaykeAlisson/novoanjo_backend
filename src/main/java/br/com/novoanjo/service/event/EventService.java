package br.com.novoanjo.service.event;

import br.com.novoanjo.domain.commons.dto.EventApproved;
import br.com.novoanjo.domain.commons.dto.EventInfoDto;
import br.com.novoanjo.domain.commons.dto.EventRequestDto;
import br.com.novoanjo.domain.model.Event;

import java.util.Set;

public interface EventService {

    /**
     * Cria novo evento
     *
     * <p>Autor: Mayke</p>
     *
     * @param idUser id usuario no banco
     * @param request EventRequestDto representacao evento
     * @return Event criado
     */
    Event create(EventRequestDto request, Long idUser);

    /**
     * Busca informação evento por id
     *
     * <p>Autor: Mayke</p>
     *
     * @param idEvent id evento no banco
     * @return EventInfoDto informações evento
     */
    EventInfoDto findById(Long idEvent);

    /**
     * Busca todos eventos aprovados
     *
     * <p>Autor: Mayke</p>
     *
     * @return Lista de eventos aprovados
     */
    Set<EventInfoDto> findAllApproved();

    /**
     * Busca eventos por estado
     *
     * <p>Autor: Mayke</p>
     *
     * @param state estado
     * @return Lista de Eventos por estado
     */
    Set<EventInfoDto> findByState(String state);

    /**
     * Busca todos eventos pendentes de aprovação
     *
     * <p>Autor: Mayke</p>
     *
     * @return Lista de Eventos pendentes
     */
    Set<EventInfoDto> findAllPendent();

    /**
     * Atualiza evento
     *
     * <p>Autor: Mayke</p>
     *
     * @param idUser id usuario no banco
     * @param idEvent id evento no banco
     * @param dto EventRequestDto representacao evento
     */
    void update(EventRequestDto dto, Long idEvent, Long idUser);

    /**
     * Aprova eventos pendentes
     *
     * <p>Autor: Mayke</p>
     *
     * @param ids ids dos eventos a ser aprovado
     */
    void aprove(EventApproved ids);

    /**
     * Deleta evento por id
     *
     * <p>Autor: Mayke</p>
     *
     * @param idUser id usuario no banco
     * @param id id evento no banco
     */
    void deleteById(Long id, Long idUser);

    /**
     * Buscar eventos pendente de aprovacao
     *
     * <p>Autor: Mayke</p>
     *
     */
    void verificaEventosPendente();
}
