package dev.oskarjohansson.projektarbetev2.model;

import org.springframework.validation.annotation.Validated;

import javax.management.relation.Role;

public record RegisterRequest(@Validated String username,@Validated String password, @Validated RoleType role, @Validated Boolean consent) {
}
