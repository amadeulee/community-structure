package io.academy.backend.academy.security.controller;

import io.academy.backend.academy.security.converter.Converters;
import io.academy.backend.academy.security.dto.UserDTO;
import io.academy.backend.academy.security.entity.User;
import io.academy.backend.academy.security.service.SecurityService;
import java.util.List;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    final SecurityService service;
    final PasswordEncoder encoder;
    final Converters converter;

    public LoginController(SecurityService service, PasswordEncoder encoder, Converters converter) {
        this.service = service;
        this.encoder = encoder;
        this.converter = converter;
    }

    @PostMapping("/api/user/create")
    public User create(@RequestBody UserDTO userDTO) {
        User user = converter.convertToUser(userDTO);

        return this.service.save(user);
    }

    @GetMapping("/api/user")
    public List<User> getAll() {
        return this.service.getAll();
    }

    @GetMapping("/api/user/{name}")
    public User get(@PathVariable("name") String name) {
        return this.service.get(name);
    }

    @DeleteMapping("/api/user/{name}")
    public User delete(@PathVariable("name") String name) {
        return this.service.delete(name);
    }
}
