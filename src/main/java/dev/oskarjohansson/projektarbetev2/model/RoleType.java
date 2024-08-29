package dev.oskarjohansson.projektarbetev2.model;

import org.springframework.security.core.GrantedAuthority;

public enum RoleType implements GrantedAuthority {
    ROLE_ADMIN,
    ROLE_USER,
    ROLE_GUEST;

    @Override
    public String getAuthority() {
        return name();
    }
}
