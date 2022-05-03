package br.com.novoanjo.domain.commons.json;

import br.com.novoanjo.domain.commons.constante.ProfileName;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class ProfileNameSerializer extends StdSerializer<ProfileName> {

    public ProfileNameSerializer() {
        super(ProfileName.class);
    }

    @Override
    public void serialize(
            final ProfileName value,
            final JsonGenerator jsonGenerator,
            final SerializerProvider serializerProvider
    ) throws IOException {
        jsonGenerator.writeString(value.getDescricao());
    }
}
