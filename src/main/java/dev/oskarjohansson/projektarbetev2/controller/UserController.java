package dev.oskarjohansson.projektarbetev2.controller;


import dev.oskarjohansson.projektarbetev2.model.ReviewRequest;
import dev.oskarjohansson.projektarbetev2.service.ReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController()
@RequestMapping("/user-controller")
@EnableMethodSecurity(securedEnabled = true)
@Secured({"SCOPE_ROLE_USER", "SCOPE_ROLE_ADMIN"})
public class UserController {

    private final ReviewService reviewService;

    public UserController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping("/v1/save-review")
    public ResponseEntity<?> saveReview(@Validated @RequestBody ReviewRequest reviewRequest, Authentication authentication) throws IllegalArgumentException {

        return ResponseEntity.ok().body(reviewService.saveReview(reviewRequest, authentication));
    }


}
