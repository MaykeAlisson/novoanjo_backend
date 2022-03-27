package br.com.novoanjo.novoanjo.service.user.impl;

import br.com.novoanjo.novoanjo.commons.dto.UserAccessDto;
import br.com.novoanjo.novoanjo.commons.dto.UserRequestDto;
import br.com.novoanjo.novoanjo.service.user.UserService;

public class UserServiceImpl implements UserService {


    @Override
    public UserAccessDto createUser(final UserRequestDto userRequest) {

        final String token = Token.gerar(user.getId(), 1L)
                .orElseThrow(() -> new BussinesException("Erro ao Gerar token!"));
        final UserAccessDto usuarioAccessDto = new UserAccessDto.Builder()
                .comIdUser(user.getId())
                .comNome(user.getNome())
                .comToken(token)
                .build();

        return null;
    }
}
