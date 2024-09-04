package dev.oskarjohansson.projektarbetev2.controller;

import dev.oskarjohansson.projektarbetev2.model.LoginRequest;
import dev.oskarjohansson.projektarbetev2.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/authentication-controller")
public class AuthenticationController {

    private final RestTemplate restTemplate;

    public AuthenticationController() {
        this.restTemplate = new RestTemplate();
    }

    @PostMapping("/v1/login")
    public ResponseEntity<String> getUser(@Validated @RequestBody LoginRequest loginRequest) throws IllegalArgumentException {
        String loginUrl = "http://localhost:8081/token-service/v1/request-token";
        String response = restTemplate.postForObject(loginUrl, loginRequest, String.class);

        return ResponseEntity.ok(response);
    }
}
