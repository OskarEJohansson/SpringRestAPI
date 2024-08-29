package dev.oskarjohansson.projektarbetev2.model;

import jakarta.validation.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;



public record RegisterRequest(@Validated @NotBlank(message = "username must not be blank") String username, @Validated @NotBlank(message = "username must not be blank") String password, @NotBlank(message = "Role must be ADMIN, USER or GUEST") RoleType role, @NotBlank(message = "Consent must be of boolean value true or false") Boolean consent) {
}
