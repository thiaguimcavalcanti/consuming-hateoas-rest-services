package com.acme.consuminghateoasrestservices.controller;

import com.acme.consuminghateoasrestservices.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;

import static java.util.Collections.emptyList;
import static org.springframework.http.RequestEntity.get;

@RequiredArgsConstructor
@RestController
public class UserController {

    private static final String EXTERNAL_API_CONTEXT_PATH = "external-context-path/";
    private static final String WIREMOCK_URL = "http://localhost:9999/" + EXTERNAL_API_CONTEXT_PATH;

    private final RestTemplate restTemplate;

    @GetMapping("/user/{id}")
    public UserDTO getUser(@PathVariable int id) {
        EntityModel<UserDTO> userDTO = executeExternalRequest("user/" + id, new ParameterizedTypeReference<>() {});
        return userDTO != null ? userDTO.getContent() : null;
    }

    @GetMapping("/getUsers")
    public Collection<UserDTO> getUsers() {
        CollectionModel<UserDTO> users = executeExternalRequest("users", new ParameterizedTypeReference<>() {});
        return users != null ? users.getContent() : emptyList();
    }

    private <T extends RepresentationModel<?>> T executeExternalRequest(String path, ParameterizedTypeReference<T> paramType) {
        return restTemplate.exchange(get(WIREMOCK_URL + path).build(), paramType).getBody();
    }
}
