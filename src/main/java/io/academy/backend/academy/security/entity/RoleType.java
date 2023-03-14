package io.academy.backend.academy.security.entity;

import java.util.Arrays;
import java.util.List;

public enum RoleType {
    ROLE_VIEW("VIEW"),
    ROLE_ADMIN("VIEW", "ADMIN"),
    ROLE_SUPER("VIEW", "ADMIN", "SUPER");

    private final List<String> roles;

    RoleType(String... roles) {
        this.roles = Arrays.asList(roles);
    }

    public boolean contains(String role) {
        return this.roles.stream()
                .anyMatch(permission -> permission.equalsIgnoreCase(role));
    }

    public List<String> getRoles() {
        return roles;
    }
}
