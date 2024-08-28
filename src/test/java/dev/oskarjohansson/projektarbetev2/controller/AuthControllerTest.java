package dev.oskarjohansson.projektarbetev2.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {

    @Autowired
    private MockMvc mvc;

    @BeforeEach
    void setUp() {
    }

    @Test
    void testFailToAccessTokenWithoutCredentials() throws Exception {
        mvc.perform(post("/token")).andExpect(status().isBadRequest());
    }

    @Test
    void testAccessTokenWithCredentials() throws Exception {
        String jsonRequest = "{\"username\":\"USER\", \"password\":\"1234\"}";

        MvcResult response = mvc.perform(post("/token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk()).andReturn();

        Assertions.assertNotNull(response.getResponse().getContentAsString());
    }
}