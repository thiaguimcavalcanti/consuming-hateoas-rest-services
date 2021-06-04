package com.acme.consuminghateoasrestservices;

import com.acme.consuminghateoasrestservices.dto.UserDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@AutoConfigureWireMock(port = 9999)
@SpringBootTest(webEnvironment = RANDOM_PORT)
class ConsumingHateoasRestServicesApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldConsumeASingleUser() throws Exception {
       String expectedResponsePayload = "{\"id\":1,\"name\":\"Thiago\",\"email\":\"thiagocavalcantireis@gmail.com\"}";

        mockMvc.perform(MockMvcRequestBuilders
                .get("/user/1")
                .accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResponsePayload));
    }

    @Test
    void shouldConsumeMultipleUsers() throws Exception {
        String expectedResponsePayload = "[{\"id\":1,\"name\":\"thiago\",\"email\":\"thiagocavalcantireis@gmail.com\",\"links\":[{\"rel\":\"self\",\"href\":\"http://localhost:9999/external-context-path/user/1\"},{\"rel\":\"tasks\",\"href\":\"http://localhost:9999/external-context-path/user/1\"}]}]";

        mockMvc.perform(MockMvcRequestBuilders
                .get("/getUsers")
                .accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResponsePayload));
    }

    private UserDTO buildUserDTO() {
        return UserDTO.builder()
                .id(1)
                .name("Thiago")
                .email("thiagocavalcantireis@gmail.com")
                .build();
    }
}
