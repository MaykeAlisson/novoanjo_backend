package br.com.novoanjo.novoanjo.service.user.impl;

import br.com.novoanjo.novoanjo.commons.constante.ProfileName;
import br.com.novoanjo.novoanjo.commons.dto.UserAccessDto;
import br.com.novoanjo.novoanjo.commons.dto.UserRequestDto;
import br.com.novoanjo.novoanjo.domain.Profile;
import br.com.novoanjo.novoanjo.domain.User;
import br.com.novoanjo.novoanjo.repository.ProfileRepository;
import br.com.novoanjo.novoanjo.repository.UserRepository;
import br.com.novoanjo.novoanjo.service.user.UserService;
import br.com.novoanjo.novoanjo.util.exception.BussinesException;
import br.com.novoanjo.novoanjo.util.jwt.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static br.com.novoanjo.novoanjo.domain.User.convertToUser;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProfileRepository profileRepository;

    @Override
    public UserAccessDto createUser(final UserRequestDto userRequest) {

        ProfileName profileName = userRequest.getProfileName();

        Profile profile = profileRepository.findByProfileName(userRequest.getProfileName())
                .orElseThrow(() -> new BussinesException("erro")); // todo mudar para notfound

        User user = userRepository.save(convertToUser(userRequest, profile));

        final String token = Token.gerar(user.getId(), user.getProfile().getId())
                .orElseThrow(() -> new BussinesException("Erro ao Gerar token!"));
        return UserAccessDto.builder()
                .id(user.getId())
                .name(user.getName())
                .profile(profile.toString())
                .token(token)
                .build();
    }
}
