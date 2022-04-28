package br.com.novoanjo.novoanjo.service;

import br.com.novoanjo.novoanjo.BaseTest;
import br.com.novoanjo.novoanjo.domain.commons.dto.UserAccessDto;
import br.com.novoanjo.novoanjo.domain.commons.dto.UserRequestDto;
import br.com.novoanjo.novoanjo.domain.commons.dto.UserRequestUpdateDto;
import br.com.novoanjo.novoanjo.domain.commons.dto.UserToServiceDto;
import br.com.novoanjo.novoanjo.domain.model.Address;
import br.com.novoanjo.novoanjo.domain.model.Phone;
import br.com.novoanjo.novoanjo.domain.model.ServiceModel;
import br.com.novoanjo.novoanjo.domain.model.User;
import br.com.novoanjo.novoanjo.infra.exception.BussinesException;
import br.com.novoanjo.novoanjo.repository.ProfileRepository;
import br.com.novoanjo.novoanjo.repository.UserRepository;
import br.com.novoanjo.novoanjo.service.user.UserService;
import org.junit.jupiter.api.*;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserServiceTest extends BaseTest {

    @Autowired
    private UserService userService;

    @Mock
    private ProfileRepository profileRepository;
    @Autowired
    private UserRepository userRepository;
    @Captor
    private ArgumentCaptor<User> userCaptor;

    @Test
    @Order(1)
    void cadastrarUsuarioSucess() throws IOException {

        UserAccessDto user = userService.createUser(getUserRequestSuccess());
        Assertions.assertNotNull(user);
        Assertions.assertNotNull(user.getId());
        Assertions.assertNotNull(user.getName());
        Assertions.assertNotNull(user.getProfile());
        Assertions.assertNotNull(user.getToken());

    }

    @Test
    @Order(2)
    void cadastrarUsuarioEmailDuplicado() throws IOException {

        final UserRequestDto userRequestSuccess = getUserRequestSuccess();
        Assertions.assertThrows(BussinesException.class, () -> userService.createUser(userRequestSuccess));

    }

    @Test
    @Order(3)
    @Transactional
    void atualizarUsuario() throws IOException {

        User userOld = getUser(1L);

        final UserRequestUpdateDto userRequestUpdateDto = getUserRequestUpdateDto();

        userService.updateUser(userRequestUpdateDto, 1L);

        User userNew = userRepository.getById(1L);

        Assertions.assertNotEquals(userOld.getPhone().getNumber(), userNew.getPhone().getNumber());
        Assertions.assertNotEquals(userOld.getPhone().getDdd(), userNew.getPhone().getDdd());
        Assertions.assertNotEquals(userOld.getAddress().getState(), userNew.getAddress().getState());

    }

    @Test
    @Order(4)
    @Transactional
    void atualizarServiceUsuario() throws IOException {

        UserToServiceDto userToServiceDto = getUserToServiceDto();

        Set<Long> ids = getUserToServiceDto().getServices();

        userService.userToService(userToServiceDto, 1L);

        User user = userRepository.getById(1L);

        Set<Long> idServices = user.getServices().stream().map(ServiceModel::getId)
                .collect(Collectors.toSet());

        Assertions.assertTrue(idServices.containsAll(ids));

    }

    @Test
    @Order(4)
    @Transactional
    void deletarServiceUsuario() throws IOException {

        UserToServiceDto userToServiceDto = getUserToServiceDelete();

        Set<Long> ids = getUserToServiceDelete().getServices();

        userService.userRemoveService(userToServiceDto, 1L);

        User user = userRepository.getById(1L);

        Set<Long> idServices = user.getServices().stream().map(ServiceModel::getId)
                .collect(Collectors.toSet());

        Assertions.assertFalse(idServices.containsAll(ids));
    }

    private User getUser(Long id) {
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

}
