package br.com.novoanjo.service.user;

import br.com.novoanjo.domain.commons.constante.ProfileName;
import br.com.novoanjo.domain.commons.dto.*;

import java.util.Set;

public interface UserService {

    /**
     * Cria novo usuario
     *
     * <p>Autor: Mayke</p>
     *
     * @param userRequest irepresentação do usuario
     * @return Usuario criado
     */
    UserAccessDto createUser(UserRequestDto userRequest);

    /**
     * Atualiza usuario
     *
     * <p>Autor: Mayke</p>
     *
     * @param id id usuario no banco
     * @param userRequestUpdate representacao usuario
     */
    void updateUser(UserRequestUpdateDto userRequestUpdate, Long id);

    /**
     * Lista de usuarios por perfil
     *
     * <p>Autor: Mayke</p>
     *
     * @param profile nome do perfil
     * @return Lista de usuarios criado
     */
    Set<UserInfoDto> findAllByProfile(ProfileName profile);

    /**
     * Lista de usuarios por servico
     *
     * <p>Autor: Mayke</p>
     *
     * @param idService id servico no banco
     * @return Lista de usuarios
     */
    Set<UserInfoDto> findAllByService(Long idService);

    /**
     * Atualiza servicos do usuarios
     *
     * <p>Autor: Mayke</p>
     *
     * @param idUser id usuario no banco
     * @param idsService ids do services
     */
    void userToService(UserToServiceDto idsService, Long idUser);

    /**
     * Remove servicos do usuario
     *
     * <p>Autor: Mayke</p>
     *
     * @param idUser id usuario no banco
     * @param userToService ids do servico
     */
    void userRemoveService(UserToServiceDto userToService, Long idUser);

    /**
     * Busca usuarios proximo ao usuario
     *
     * <p>Autor: Mayke</p>
     *
     * @param idUser id usuario no banco
     * @return Lista de usuarios perfil S proximo ao usuario
     **/
    Set<UserInfoDto> userDiscoverService(Long idUser);
}
