package io.academy.backend.academy.security.entity;

import java.util.UUID;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.security.core.GrantedAuthority;

public class Role implements GrantedAuthority {
    private RoleType role;

    public Role(RoleType role) {
        this.role = role;
    }

    public RoleType getRole() {
        return role;
    }

    public void setRole(RoleType role) {
        this.role = role;
    }

    @Override
    public String getAuthority() {
        return this.role.toString();
    }

    @Override
    public String toString() {
        return "Role{" +
                "role=" + role +
                '}';
    }
}
