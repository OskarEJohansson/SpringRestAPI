package dev.oskarjohansson.projektarbetev2.controller;

import dev.oskarjohansson.projektarbetev2.model.MyUser;
import dev.oskarjohansson.projektarbetev2.model.RegisterRequest;
import dev.oskarjohansson.projektarbetev2.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;

@RestController
@EnableMethodSecurity(securedEnabled = true)
@Secured("SCOPE_ROLE_ADMIN")
@RequestMapping("/admin-controller")
public class AdminController {

    UserService userService;

    AdminController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/v1/save-user")
    public ResponseEntity<?> saveUser(@RequestBody RegisterRequest registerRequest, Authentication authentication) throws IllegalArgumentException {
        Optional<MyUser> response = userService.saveUser(registerRequest);

        return ResponseEntity.ok(response.get());
    }

    @GetMapping("/v1/test-security")
    public ResponseEntity<String> testMethodSecurity(Principal principal){
        return ResponseEntity.ok("Hello " + principal.getName());
    }
}
