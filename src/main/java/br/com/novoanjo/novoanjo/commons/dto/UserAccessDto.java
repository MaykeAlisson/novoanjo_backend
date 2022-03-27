package br.com.novoanjo.novoanjo.commons.dto;

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

    private String name;
    private String profile;
    private String token;

}
