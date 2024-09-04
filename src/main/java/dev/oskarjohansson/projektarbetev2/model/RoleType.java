package dev.oskarjohansson.projektarbetev2.model;

import org.springframework.security.core.GrantedAuthority;

public enum RoleType implements GrantedAuthority {
    ADMIN,
    USER,
    GUEST;

    @Override
    public String getAuthority() {
        return name();
    }
}
