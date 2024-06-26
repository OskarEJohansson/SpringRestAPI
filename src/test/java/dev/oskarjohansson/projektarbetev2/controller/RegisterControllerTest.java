package dev.oskarjohansson.projektarbetev2.controller;

import dev.oskarjohansson.projektarbetev2.configuration.SecurityConfiguration;
import dev.oskarjohansson.projektarbetev2.service.MyUserDetailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RegisterController.class)
@Import(SecurityConfiguration.class)

class RegisterControllerTest {

    @Autowired
    MockMvc client;

    @MockBean
    private MyUserDetailService myUserDetailService;
    @MockBean
    private JwtEncoder jwtEncoder;
    @MockBean
    private JwtClaimsSet jwtClaimsSet;
    @MockBean
    private JwtDecoder jwtDecoder;


    @BeforeEach
    void setUp() {
    }

    @Test
    void registerUserWithNonValidPasswordLength() throws Exception {
        String jsonRequest = "{\"username\":\"USER\", \"password\":\"1234\"}";

        client.perform(post("/register").contentType(MediaType.APPLICATION_JSON).content(jsonRequest)).andExpect(status().is4xxClientError()).andReturn();
    }

    @Test
    void registerUserWithNonValidUserLength() throws Exception {
        String jsonRequest = "{\"username\":\"USE\", \"password\":\"1234\"}";

        client.perform(post("/register").contentType(MediaType.APPLICATION_JSON).content(jsonRequest)).andExpect(status().is4xxClientError());
    }

    @Test
    void testRegisterUserWithValidUsernameAndPassword() throws Exception {
        String jsonRequest = "{\"username\":\"USEr\", \"password\":\"123456\"}";

        client.perform(post("/register").contentType(MediaType.APPLICATION_JSON).content(jsonRequest)).andExpect(status().isOk());
    }
}