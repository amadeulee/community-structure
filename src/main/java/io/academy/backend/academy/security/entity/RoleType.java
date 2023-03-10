package io.academy.backend.academy.security.entity;

import java.util.Arrays;
import java.util.List;

public enum RoleType {
    VIEW("VIEW"),
    ADMIN("VIEW", "ADMIN"),
    SUPER("VIEW", "ADMIN", "SUPER");

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
