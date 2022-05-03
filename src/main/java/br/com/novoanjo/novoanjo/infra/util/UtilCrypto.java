package br.com.novoanjo.novoanjo.infra.util;


import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class UtilCrypto {

    public static String encriptar(final String texto) {

        return new BCryptPasswordEncoder().encode(texto);

    }
}
