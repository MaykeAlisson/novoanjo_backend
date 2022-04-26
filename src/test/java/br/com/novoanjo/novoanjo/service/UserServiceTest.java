package br.com.novoanjo.novoanjo.service;

import br.com.novoanjo.novoanjo.BaseTest;
import br.com.novoanjo.novoanjo.domain.commons.dto.UserAccessDto;
import br.com.novoanjo.novoanjo.repository.ProfileRepository;
import br.com.novoanjo.novoanjo.repository.UserRepository;
import br.com.novoanjo.novoanjo.service.user.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
class UserServiceTest extends BaseTest {

    @Autowired
    private UserService userService;

    @Mock
    private ProfileRepository profileRepository;

    @Mock
    private UserRepository userRepository;

    @Test
    void cadastrarUsuarioSucess() throws IOException {

        UserAccessDto user = userService.createUser(getUserRequestSuccess());

//        Assertions.assertNotNull(insert);
    }

    @Test
    void cadastrarUsuarioEmailDuplicado() {

//        Assertions.assertNotNull(insert);
    }

    @Test
    void atualizarUsuario() {

//        Assertions.assertNotNull(insert);
    }

    @Test
    void atualizarServiceUsuario() {

//        Assertions.assertNotNull(insert);
    }

    @Test
    void deletarServiceUsuario() {

//        Assertions.assertNotNull(insert);
    }

}
