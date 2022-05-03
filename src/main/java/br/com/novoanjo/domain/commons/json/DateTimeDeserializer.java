package br.com.novoanjo.domain.commons.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeDeserializer extends StdDeserializer<LocalDateTime> {

    public DateTimeDeserializer() {
        this(null);
    }

    private DateTimeDeserializer(final Class<LocalDateTime> vc) {
        super(vc);
    }

    public LocalDateTime deserialize(final JsonParser jsonParser, final DeserializationContext ctxt) throws IOException {
        return jsonParser != null && StringUtils.isNotBlank(jsonParser.getText()) ? LocalDateTime.parse(jsonParser.getText(), DateTimeFormatter.ISO_LOCAL_DATE_TIME) : null;
    }
}
