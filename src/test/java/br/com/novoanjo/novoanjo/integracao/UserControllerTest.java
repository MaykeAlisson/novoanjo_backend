package br.com.novoanjo.novoanjo.integracao;

import br.com.novoanjo.novoanjo.BaseTest;
import br.com.novoanjo.novoanjo.domain.commons.dto.UserAccessDto;
import br.com.novoanjo.novoanjo.repository.UserRepository;
import br.com.novoanjo.novoanjo.service.user.UserService;
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

class UserControllerTest extends BaseTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @Test
    void deveRetornarSucessoAoCriarUsuario() throws Exception {

        when(userService.createUser(any()))
                .thenReturn(new UserAccessDto());

        String body = readStringFromFile(JSON_USER_REQUEST_SUCCESS);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post(URL_USER)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(body);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.CREATED.value(), response.getStatus());

    }

    @Test
    void deveRetornarSucessoAoAtualizarUsuario() throws Exception {

        when(userRepository.save(any()))
                .thenReturn(Optional.of(getUser(1L)));

        userService.updateUser(getUserRequestUpdateDto(), 1L);

        String body = readStringFromFile(JSON_USER_REQUEST_UPDATE);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.put(URL_USER)
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, format("Bearer %s", TOKEN_S))
                .accept(MediaType.APPLICATION_JSON)
                .content(body);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());

    }

    @Test
    void buscarUsuarioPorPerfilErroPosUsuarioNaoTemPerfilMaster() throws Exception {

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(format("%s/A", URL_USER_BUSCA_POR_PROFILE))
                .header(HttpHeaders.AUTHORIZATION, format("Bearer %s", TOKEN_S))
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatus());

    }

    @Test
    void buscarUsuarioPorServiceIdErroPosUsuarioNaoTemPerfilMaster() throws Exception {

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(format("%s/1", URL_USER_BUSCAR_POR_ID_SERVICE))
                .header(HttpHeaders.AUTHORIZATION, format("Bearer %s", TOKEN_S))
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatus());

    }
}
