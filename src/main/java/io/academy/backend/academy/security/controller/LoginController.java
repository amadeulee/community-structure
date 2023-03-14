package io.academy.backend.academy.security.controller;

import io.academy.backend.academy.security.converter.Converters;
import io.academy.backend.academy.security.dto.LoginUserDTO;
import io.academy.backend.academy.security.dto.UserDTO;
import io.academy.backend.academy.security.entity.User;
import io.academy.backend.academy.security.jwt.JWTUtils;
import io.academy.backend.academy.security.service.SecurityService;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    final AuthenticationManager authenticator;
    final JWTUtils jwtUtils;

    @Value("${security.auth.secret}")
    private String secret;


    public LoginController(SecurityService service, PasswordEncoder encoder, Converters converter, AuthenticationManager authenticationManager, JWTUtils jwtUtils) {
        this.service = service;
        this.encoder = encoder;
        this.converter = converter;
        this.authenticator = authenticationManager;
        this.jwtUtils = jwtUtils;
    }

    private Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }


    @PostMapping("/api/login")
    public Map<String, Object> login(@RequestBody LoginUserDTO login) {
        String username = login.getUsername();
        String password = login.getPassword();
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);

        try {
            authenticator.authenticate(token);
            String jwt = jwtUtils.generateToken(login.getUsername(), secret);
            return Collections.singletonMap("jwt-token", jwt);
        }catch (RuntimeException exception) {
            System.out.println(exception.getMessage());
        }
        return Collections.singletonMap("jwt-token", null);
    }

    @PostMapping("/api/user/create")
    public User create(@RequestBody UserDTO userDTO) {
        var authentication = getAuthentication();
        String username = authentication.getPrincipal().toString();
        System.out.println(username);


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
