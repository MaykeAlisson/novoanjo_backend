package br.com.novoanjo.novoanjo.service.user;

import br.com.novoanjo.novoanjo.domain.commons.constante.ProfileName;
import br.com.novoanjo.novoanjo.domain.commons.dto.*;

import java.util.Set;

public interface UserService {

    UserAccessDto createUser(UserRequestDto userRequest);

    void updateUser(UserRequestUpdateDto userRequestUpdate, Long id);

    Set<UserInfoDto> findAllByProfile(ProfileName profile);

    Set<UserInfoDto> findAllByService(Long idService);

    void userToService(UserToServiceDto idsService, Long idUser);

    void userRemoveService(UserToServiceDto userToService, Long idUser);

    Set<UserInfoDto> userDiscoverService(Long idUser);
}
