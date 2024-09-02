package dev.oskarjohansson.projektarbetev2.controller;


import dev.oskarjohansson.projektarbetev2.model.LoginRequest;
import dev.oskarjohansson.projektarbetev2.model.Review;
import dev.oskarjohansson.projektarbetev2.model.ReviewRequest;
import dev.oskarjohansson.projektarbetev2.service.ReviewService;
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

import java.security.Principal;

@RestController()
@RequestMapping("/user-controller")
@EnableMethodSecurity(securedEnabled = true)
@Secured({"SCOPE_ROLE_USER", "SCOPE_ROLE_ADMIN"})
public class UserController {

    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;
    private final RestTemplate restTemplate;
    private final ReviewService reviewService;

    public UserController(UserService userService, ReviewService reviewService) {
        this.userService = userService;
        this.restTemplate = new RestTemplate();
        this.reviewService = reviewService;
    }

    @PostMapping("/v1/login")
    public ResponseEntity<String> getUser(@Validated @RequestBody LoginRequest loginRequest) throws IllegalArgumentException {
            String loginUrl = "http://localhost:8081/token-service/v1/request-token";
            String response = restTemplate.postForObject(loginUrl, loginRequest, String.class);
            System.out.println(response);

            return ResponseEntity.ok(response);
    }

    @PostMapping("/v1/save-review")
    public ResponseEntity<?> saveReview(@Validated ReviewRequest reviewRequest) throws IllegalArgumentException{

        return ResponseEntity.ok().body(reviewService.saveReview(reviewRequest)) ;

    }


}
