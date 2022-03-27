package br.com.novoanjo.novoanjo.service.user;

import br.com.novoanjo.novoanjo.commons.dto.UserAccessDto;
import br.com.novoanjo.novoanjo.commons.dto.UserRequestDto;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    UserAccessDto createUser(UserRequestDto userRequest);
}
