package dev.oskarjohansson.projektarbetev2.controller;


import dev.oskarjohansson.projektarbetev2.model.LoginRequest;
import dev.oskarjohansson.projektarbetev2.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;

@RestController()
@RequestMapping("/user-controller")
@EnableMethodSecurity(securedEnabled = true)
@Secured({"SCOPE_ROLE_USER", "SCOPE_ROLE_ADMIN"})
public class UserController {

    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;
    private RestTemplate restTemplate;

    public UserController(UserService userService) {
        this.userService = userService;
        this.restTemplate = new RestTemplate();
    }

    @PostMapping("/v1/login")
    public ResponseEntity<?> getUser(@Validated @RequestBody LoginRequest loginRequest) throws IllegalArgumentException {
            String loginUrl = "http://localhost:8081/token-service/v1/request-token";
            String response = restTemplate.postForObject(loginUrl, loginRequest, String.class);
            System.out.println(response);

            return ResponseEntity.ok(response);
    }

    @GetMapping("/v1/show-credentials")
    public ResponseEntity<?> showCredentials(Authentication authentication){

        return ResponseEntity.ok(authentication.toString());
    }

    @GetMapping("/v1/show-token")
    public ResponseEntity<?> showToken(@RequestHeader("Authorization") String token) {
        token = token.replace("Bearer ", "");

        String[] chunks = token.split("\\.");
        Base64.Decoder decoder = Base64.getUrlDecoder();

        String payload = new String(decoder.decode(chunks[1]));
        return ResponseEntity.ok(payload);
    }
}
