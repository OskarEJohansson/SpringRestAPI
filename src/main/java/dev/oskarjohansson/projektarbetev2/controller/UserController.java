package dev.oskarjohansson.projektarbetev2.controller;


import dev.oskarjohansson.projektarbetev2.model.LoginRequest;
import dev.oskarjohansson.projektarbetev2.model.MyUser;
import dev.oskarjohansson.projektarbetev2.model.RegisterRequest;
import dev.oskarjohansson.projektarbetev2.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@RestController()
@RequestMapping("/user-controller")
@EnableMethodSecurity(securedEnabled = true)
public class UserController {

    private final UserService userService;
    private RestTemplate restTemplate;

    public UserController(UserService userService) {
        this.userService = userService;
        this.restTemplate = new RestTemplate();
    }

    @PostMapping("/v1/save-user")
    public ResponseEntity<?> saveUser(@RequestBody RegisterRequest registerRequest) throws IllegalArgumentException{
        Optional<MyUser> response = userService.saveUser(registerRequest);

        return ResponseEntity.ok(response.get());

    }

    @PostMapping("/v1/login")
    public ResponseEntity<?> getUser(@Validated @RequestBody LoginRequest loginRequest) throws Exception{

        try {
            String loginUrl = "http://localhost:8081/token-service/v1/request-token";
            String response = restTemplate.postForObject(loginUrl, loginRequest, String.class);
            System.out.println(response);

            return ResponseEntity.ok(response);
        } catch (Exception ex){

            return ResponseEntity.internalServerError().body(ex.getMessage());
        }

    }
}
