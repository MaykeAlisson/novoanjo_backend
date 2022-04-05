package br.com.novoanjo.novoanjo.service.user;

import br.com.novoanjo.novoanjo.domain.commons.constante.ProfileName;
import br.com.novoanjo.novoanjo.domain.commons.dto.UserAccessDto;
import br.com.novoanjo.novoanjo.domain.commons.dto.UserByProfileDto;
import br.com.novoanjo.novoanjo.domain.commons.dto.UserRequestDto;
import br.com.novoanjo.novoanjo.domain.commons.dto.UserRequestUpdateDto;
import br.com.novoanjo.novoanjo.domain.model.Profile;
import org.springframework.stereotype.Service;

import java.util.Set;

public interface UserService {

    UserAccessDto createUser(UserRequestDto userRequest);

    void updateUser(UserRequestUpdateDto userRequestUpdate, Long id);

    Set<UserByProfileDto> findAllByProfile(ProfileName profile);
}
