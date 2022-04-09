package br.com.novoanjo.novoanjo.domain.commons.dto;

import br.com.novoanjo.novoanjo.domain.model.Event;
import br.com.novoanjo.novoanjo.domain.model.Phone;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

import static br.com.novoanjo.novoanjo.domain.commons.dto.AddressRequestDto.toAddress;
import static br.com.novoanjo.novoanjo.domain.commons.dto.PhoneRequestDto.toPhone;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventInfoDto implements Serializable {

    private String name;
    private String description;
    private LocalDateTime data;
    private AddressRequestDto address;
    private PhoneRequestDto phone;

    public static EventInfoDto toEventInfo(Event event){
        return EventInfoDto.builder()
                .name(event.getName())
                .description(event.getDescription())
                .data(event.getData())
                .address(toAddress(event.getAddress()))
                .phone(toPhone(event.getUser().getPhone()))
                .build();
    }

}
