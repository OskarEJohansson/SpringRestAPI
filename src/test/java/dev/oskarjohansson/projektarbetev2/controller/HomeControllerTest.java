package dev.oskarjohansson.projektarbetev2.controller;

import dev.oskarjohansson.projektarbetev2.configuration.SecurityConfiguration;
import dev.oskarjohansson.projektarbetev2.service.MyUserDetailService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(HomeController.class)
@Import(SecurityConfiguration.class)
class HomeControllerTest {

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

    @Test
    void testGetRootWithoutUserAndAssertHelloGuest() throws Exception {

       MvcResult response = client.perform(get("/"))
                .andExpect(status().isOk()).andReturn();

       String msg = response.getResponse().getContentAsString();
        Assertions.assertEquals(msg, "Hello, Guest");
    }

    @Test
    @WithMockUser(username = "User")
    void testGetRootWithUserAndAssertHelloPrincipalGetName() throws Exception {

        MvcResult response = client.perform(get("/"))
                .andExpect(status().isOk()).andReturn();
        String msg = response.getResponse().getContentAsString();

        Assertions.assertEquals(msg, "Hello, User");
    }

    @Test
    @WithMockUser(roles = "SCOPE_ROLE_ADMIN")
    void testGetUserWithUnAuthorisedUserAndAssert403 () throws Exception {

        client.perform(get("/user")).andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "User", authorities = "SCOPE_ROLE_USER")
    void testGetUserWithAuthorisedUserAndAssert200AndMessageHelloUser () throws Exception {
        MvcResult response = client.perform(get("/user")).andExpect(status().isOk()).andReturn();
        String msg = response.getResponse().getContentAsString();
        Assertions.assertEquals(msg, "Hello, User");
    }

    @Test
    @WithMockUser(username = "Admin", authorities = "SCOPE_ROLE_ADMIN")
    void testGetAdminWithAuthorisedUserAndAssert200AndMessageHelloUser() throws Exception{
        MvcResult response = client.perform(get("/admin")).andExpect(status().isOk()).andReturn();
        String msg = response.getResponse().getContentAsString();
        Assertions.assertEquals(msg, "Hello, Admin");

    }
}