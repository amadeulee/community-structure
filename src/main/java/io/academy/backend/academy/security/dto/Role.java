package io.academy.backend.academy.security.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.academy.backend.academy.security.entity.RoleType;

public class Role {
    private String role;

    public Role(String role) {
        this.role = role;
    }

    public Role() {}

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "Role{" +
                "role='" + role + '\'' +
                '}';
    }
}
