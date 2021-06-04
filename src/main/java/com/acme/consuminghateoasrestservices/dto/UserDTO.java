package com.acme.consuminghateoasrestservices.dto;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
public class UserDTO extends RepresentationModel<UserDTO> {
    private int id;
    private String name;
    private String email;
}
