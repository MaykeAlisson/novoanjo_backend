package br.com.novoanjo.novoanjo.config.jwt;

import br.com.novoanjo.novoanjo.infra.util.UtilDate;
import br.com.novoanjo.novoanjo.config.exception.BussinesException;
import io.jsonwebtoken.*;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.StringJoiner;

import static java.time.LocalDateTime.now;
import static org.apache.commons.lang3.StringUtils.isEmpty;

@Service
public class Token implements Serializable {

    public static final LocalDateTime JWT_TOKEN_VALIDITY = now().plusHours(3);
    public static final String SECRET = "javaword_novoAnjo";
    public static final Integer SUB_STRING_TOKEN = 7;
    public static final Integer QTD_PARANS_TOKEN = 3;

    private static void checkGerar(final Long idUsuario, final String perfil, final LocalDateTime dataExpiracao) {
        StringJoiner check = new StringJoiner(" ");
        if (idUsuario == null) {
            check.add("[ idUsuario ]");
        }

        if (perfil == null) {
            check.add("[ idPerfil ]");
        }

        if (!UtilDate.isValida(dataExpiracao)) {
            check.add("[ dataExpiracao ]");
        }

        if (check.length() > 0) {
            throw new IllegalArgumentException(String.format("Faltou definir arg(s): %s para gerar Token", check));
        }
    }

    private static void checkGerar(final Long idUsuario, final String perfil) {
        StringJoiner check = new StringJoiner(" ");
        if (idUsuario == null) {
            check.add("[ idUsuario ]");
        }

        if (perfil == null) {
            check.add("[ idPerfil ]");
        }

        if (check.length() > 0) {
            throw new IllegalArgumentException(String.format("Faltou definir arg(s): %s para gerar Token", check));
        }
    }

    public static Optional<String> gerar(
            final Long idUsuario,
            final String perfil,
            final LocalDateTime dataExpiracao
    ) {
        checkGerar(idUsuario, perfil, dataExpiracao);
        return Optional.ofNullable(Jwts.builder()
                .setSubject(createSubject(idUsuario, perfil))
                .setExpiration(UtilDate.toDate(dataExpiracao))
                .signWith(SignatureAlgorithm.HS256, SECRET)
                .compact());
    }

    public static Optional<String> gerar(final Long idUsuario, final String perfil) {
        checkGerar(idUsuario, perfil);
        return Optional.ofNullable(Jwts.builder()
                .setSubject(createSubject(idUsuario, perfil))
                .setExpiration(UtilDate.toDate(JWT_TOKEN_VALIDITY))
                .signWith(SignatureAlgorithm.HS256, SECRET)
                .compact());
    }

    private static String createSubject(final Long idUsuario, final String perfil) {
        return String.format("%s;%s;%s", idUsuario, perfil, LocalDate.now());
    }

    public static Boolean isValid(final String token) {
        try {
            if (StringUtils.isBlank(token)) {
                return false;
            }
            Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public static Long getUserId(final String token) {
        try {
            String subject = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody().getSubject();
            if (StringUtils.isBlank(subject)) {
                throw new BussinesException("Não foi possivei recuperar informações do token!");
            }
            String[] decode = subject.split(";");
            return Long.valueOf(decode[0]);
        } catch (Exception e) {
            throw new BussinesException("Não foi possivei recuperar informações do token! " + e.getMessage());
        }
    }

    public static Long getUserId() {
        try {
            final HttpServletRequest request = getCurrentHttpRequest();
            final String possivelToken = request.getHeader("Authorization");
            if (isEmpty(possivelToken) || !possivelToken.startsWith("Bearer ")) {
                throw new BussinesException("Não foi possivei recuperar informações do token!");
            }
            final String token = possivelToken.substring(SUB_STRING_TOKEN);
            String subject = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody().getSubject();
            if (StringUtils.isBlank(subject)) {
                throw new BussinesException("Não foi possivei recuperar informações do token!");
            }
            String[] decode = subject.split(";");
            return Long.valueOf(decode[0]);
        } catch (Exception e) {
            throw new BussinesException("Não foi possivei recuperar informações do token! " + e.getMessage());
        }

    }

    public static String getUserPerfil() {
        try {
            final HttpServletRequest request = getCurrentHttpRequest();
            final String possivelToken = request.getHeader("Authorization");
            if (isEmpty(possivelToken) || !possivelToken.startsWith("Bearer ")) {
                throw new BussinesException("Não foi possivei recuperar informações do token!");
            }
            final String token = possivelToken.substring(SUB_STRING_TOKEN);
            String subject = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody().getSubject();
            if (StringUtils.isBlank(subject)) {
                throw new BussinesException("Não foi possivei recuperar informações do token!");
            }
            String[] decode = subject.split(";");
            return String.valueOf(decode[1]);
        } catch (Exception e) {
            throw new BussinesException("Não foi possivei recuperar informações do token! " + e.getMessage());
        }

    }

    public static Optional<Token.Value> decode(final String token) {
        if (StringUtils.isBlank(token)) {
            return Optional.empty();
        } else {
            try {
                String subject = ((Claims) Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody()).getSubject();
                if (StringUtils.isBlank(subject)) {
                    return Optional.empty();
                } else {
                    String[] decode = subject.split(";");
                    return ArrayUtils.getLength(decode)
                            != QTD_PARANS_TOKEN ? Optional.empty()
                            : Optional.of(Token.Value.newInstance(String.valueOf(decode[0]), Long.valueOf(decode[1])));
                }
            } catch (ExpiredJwtException var3) {
                return Optional.of(Token.Value.newInstance("ACESSO EXPIROU - NECESSARIO NOVA AUTENTICACAO"));
            } catch (UnsupportedJwtException var4) {
                return Optional.empty();
            } catch (IllegalArgumentException | MalformedJwtException var5) {
                return Optional.of(Token.Value.newInstance("TOKEN INVALIDO - CONTATE O DEPTO DE TI"));
            }
        }

    }


    public interface Value {
        String getIdUsuario();

        Long getIdPerfil();

        String getMsgInconsistencia();

        static Token.Value newInstance(final String idUsuario, final Long idPerfil, final String msgInconsistencia) {
            return new Token.Value() {
                public String getIdUsuario() {
                    return idUsuario;
                }

                public Long getIdPerfil() {
                    return idPerfil;
                }

                public String getMsgInconsistencia() {
                    return msgInconsistencia;
                }
            };
        }

        static Token.Value newInstance(final String idUsuario, final Long idPerfil) {
            return newInstance(idUsuario, idPerfil, (String) null);
        }

        static Token.Value newInstance(final String mensagem) {
            return newInstance((String) null, (Long) null, mensagem);
        }
    }

    private static HttpServletRequest getCurrentHttpRequest() {
        return Optional.ofNullable(RequestContextHolder.getRequestAttributes())
                .filter(ServletRequestAttributes.class::isInstance)
                .map(ServletRequestAttributes.class::cast)
                .map(ServletRequestAttributes::getRequest)
                .orElseThrow(() -> new BussinesException("Não foi possivel recuperar request"));
    }
}
