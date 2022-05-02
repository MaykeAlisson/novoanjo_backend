package br.com.novoanjo.novoanjo.integracao;

import br.com.novoanjo.novoanjo.BaseTest;
import br.com.novoanjo.novoanjo.domain.commons.dto.EventRequestDto;
import br.com.novoanjo.novoanjo.repository.EventRepository;
import br.com.novoanjo.novoanjo.repository.UserRepository;
import br.com.novoanjo.novoanjo.service.event.EventService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;

import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class EventControllerTest extends BaseTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private EventService eventService;

    @MockBean
    private EventRepository eventRepository;

    @MockBean
    private UserRepository userRepository;

    @Test
    void deveRetornarSucessoAoCriarEvento() throws Exception {

        when(userRepository.findById(any()))
                .thenReturn(Optional.of(getUser(1L)));

        when(eventRepository.save(any()))
                .thenReturn(getEvent());
        
        EventRequestDto eventRequestDto = getEventRequestDto();

        eventService.create(eventRequestDto, 1L);

        String body = readStringFromFile(JSON_EVENT_REQUEST);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post(URL_EVENT)
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, format("Bearer %s", TOKEN_S))
                .accept(MediaType.APPLICATION_JSON)
                .content(body);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.CREATED.value(), response.getStatus());

    }

    @Test
    void buscarEventosPendentesErroPosUsuarioNaoTemPerfilMaster() throws Exception {}

    @Test
    void aprovarEventosPendentesErroPosUsuarioNaoTemPerfilMaster() throws Exception {}


}
