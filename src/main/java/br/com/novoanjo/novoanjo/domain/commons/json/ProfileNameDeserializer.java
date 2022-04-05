package br.com.novoanjo.novoanjo.domain.commons.json;

import br.com.novoanjo.novoanjo.domain.commons.constante.ProfileName;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

import static java.util.Objects.nonNull;
import static org.apache.commons.lang3.EnumUtils.getEnum;

public class ProfileNameDeserializer extends StdDeserializer<ProfileName> {

    public ProfileNameDeserializer() {
        this(null);
    }

    protected ProfileNameDeserializer(final Class<ProfileName> clazz) {
        super(clazz);
    }

    @Override
    public ProfileName deserialize(
            final JsonParser jsonParser,
            final DeserializationContext deserializationContext
    ) throws IOException {
        return nonNull(jsonParser)
                ? getEnum(ProfileName.class, jsonParser.getText())
                : null;
    }
}
