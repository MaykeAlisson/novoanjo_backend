package br.com.novoanjo.novoanjo.domain.commons.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserAccessDto implements Serializable {

    private Long id;
    private String name;
    private String profile;
    private String token;

}
