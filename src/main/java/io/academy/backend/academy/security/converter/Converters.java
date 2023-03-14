package io.academy.backend.academy.security.converter;

import io.academy.backend.academy.security.dto.UserDTO;
import io.academy.backend.academy.security.entity.Role;
import io.academy.backend.academy.security.entity.RoleType;
import io.academy.backend.academy.security.entity.User;
import java.util.ArrayList;
import java.util.List;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class Converters {
    final PasswordEncoder encoder;

    public Converters(PasswordEncoder encoder) {
        this.encoder = encoder;
    }

    public User convertToUser(UserDTO userDto) {
        String username = userDto.getName();
        String password = encoder.encode(userDto.getPassword());
        List<Role> roles = convertStringsToRoles(userDto.getRoles());

        return new User(username, password, roles);
    }

    public List<Role> convertStringsToRoles(List<io.academy.backend.academy.security.dto.Role> stringRoles) {
        List<Role> roles = new ArrayList<>();

        stringRoles
                .forEach(roleDto -> roles.add(new Role(RoleType.valueOf(roleDto.getRole()))));
        return roles;
    }
}
