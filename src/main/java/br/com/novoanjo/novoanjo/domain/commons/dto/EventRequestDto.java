package br.com.novoanjo.novoanjo.domain.commons.dto;

import br.com.novoanjo.novoanjo.domain.commons.json.DateTimeDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventRequestDto implements Serializable {

    @NotBlank(message = "name is mandatory")
    private String name;

    @NotBlank(message = "description is mandatory")
    private String description;

    @NotNull
    @JsonDeserialize(using = DateTimeDeserializer.class)
    private LocalDateTime data;

    @NotNull
    private AddressRequestDto address;

}
