package br.com.novoanjo.novoanjo.service.user.impl;

import br.com.novoanjo.novoanjo.commons.dto.UserAccessDto;
import br.com.novoanjo.novoanjo.commons.dto.UserRequestDto;
import br.com.novoanjo.novoanjo.domain.User;
import br.com.novoanjo.novoanjo.repository.UserRepository;
import br.com.novoanjo.novoanjo.service.user.UserService;
import br.com.novoanjo.novoanjo.util.exception.BussinesException;
import br.com.novoanjo.novoanjo.util.jwt.Token;
import org.springframework.beans.factory.annotation.Autowired;

import static br.com.novoanjo.novoanjo.domain.User.convert;

public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserAccessDto createUser(final UserRequestDto userRequest) {

        User user = userRepository.save(convert(userRequest));

        final String token = Token.gerar(user.getId(), user.getProfile().getId())
                .orElseThrow(() -> new BussinesException("Erro ao Gerar token!"));
        final UserAccessDto usuarioAccessDto = new UserAccessDto.
                .comIdUser(user.getId())
                .comNome(user.getNome())
                .comToken(token)
                .build();

        return null;
    }
}
