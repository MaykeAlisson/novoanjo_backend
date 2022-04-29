package br.com.novoanjo.novoanjo;

import br.com.novoanjo.novoanjo.domain.commons.constante.Approved;
import br.com.novoanjo.novoanjo.domain.commons.dto.EventRequestDto;
import br.com.novoanjo.novoanjo.domain.commons.dto.UserRequestDto;
import br.com.novoanjo.novoanjo.domain.commons.dto.UserRequestUpdateDto;
import br.com.novoanjo.novoanjo.domain.commons.dto.UserToServiceDto;
import br.com.novoanjo.novoanjo.domain.model.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Collections;

public abstract class BaseTest {

    protected static final String JSON_USER_REQUEST_SUCCESS = "json/request/user_create_success.json";
    protected static final String JSON_USER_REQUEST_UPDATE = "json/request/user_update_success.json";
    protected static final String JSON_USER_SERVICE_REQUEST = "json/request/user_service_success.json";

    protected static final String JSON_USER_SERVICE_REMOVE_REQUEST = "json/request/user_service_delete.json";

    protected static final String JSON_EVENT_REQUEST = "json/request/event_create.json";

    @Autowired
    private ObjectMapper objectMapper;

    protected <T> T readJsonAndParse(String filename, Class<T> clazz) throws IOException {
        InputStream inputStream = new ClassPathResource(filename).getInputStream();
        Reader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
        T readValue = objectMapper.readValue(reader, clazz);
        return readValue;
    }

    protected UserRequestDto getUserRequestSuccess() throws IOException {
        return readJsonAndParse(JSON_USER_REQUEST_SUCCESS, UserRequestDto.class);
    }

    protected UserRequestUpdateDto getUserRequestUpdateDto() throws IOException {
        return readJsonAndParse(JSON_USER_REQUEST_UPDATE, UserRequestUpdateDto.class);
    }

    protected UserToServiceDto getUserToServiceDto() throws IOException {
        return readJsonAndParse(JSON_USER_SERVICE_REQUEST, UserToServiceDto.class);
    }

    protected UserToServiceDto getUserToServiceDelete() throws IOException {
        return readJsonAndParse(JSON_USER_SERVICE_REMOVE_REQUEST, UserToServiceDto.class);
    }

    protected EventRequestDto getEventRequestDto() throws IOException {
        return readJsonAndParse(JSON_EVENT_REQUEST, EventRequestDto.class);
    }

    protected User getUser(Long id) {
        Address address = Address.builder()
                .id(1L)
                .city("Teste Cidade")
                .state("SP")
                .number(12L)
                .logradouro("Rua Teste")
                .zipCode("111111")
                .build();

        ServiceModel service = ServiceModel.builder()
                .serviceName("Service1")
                .build();

        Phone phone = Phone.builder()
                .ddd((short) 34)
                .number(1234556L)
                .build();

        return User.builder()
                .id(id)
                .name("Teste")
                .address(address)
                .services(Collections.singleton(service))
                .phone(phone)
                .build();
    }

    protected Event getEvent() {
        Address address = Address.builder()
                .id(1L)
                .city("Teste Cidade")
                .state("SP")
                .number(12L)
                .logradouro("Rua Teste")
                .zipCode("111111")
                .build();

        return Event.builder()
                .id(1L)
                .name("Evento Teste")
                .description("Descrição Evento")
                .data(LocalDateTime.now())
                .approved(Approved.N)
                .user(getUser(1L))
                .address(address)
                .build();
    }

}
