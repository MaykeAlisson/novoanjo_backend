package br.com.novoanjo.novoanjo;

import br.com.novoanjo.novoanjo.domain.commons.dto.UserRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

public abstract class BaseTest {

    protected static final String JSON_USER_REQUEST_SUCCESS = "json/user_create_success.json";

    @Autowired
    private ObjectMapper objectMapper;

    protected <T> T readJsonAndParse(String filename, Class<T> clazz) throws IOException {
        InputStream inputStream = new ClassPathResource(filename).getInputStream();
        Reader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
        T readValue = objectMapper.readValue(reader, clazz);
        return readValue;
    }


    protected UserRequestDto getUserRequestSuccess() throws IOException {
        return readJsonAndParse(JSON_USER_REQUEST_SUCCESS, UserRequestDto.class);
    }
}
