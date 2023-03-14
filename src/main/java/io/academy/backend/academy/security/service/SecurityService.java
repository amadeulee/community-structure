package io.academy.backend.academy.security.service;

import io.academy.backend.academy.security.entity.User;
import io.academy.backend.academy.security.repository.UserRepositoryInterface;
import java.util.List;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class SecurityService {
    final UserRepositoryInterface repository;

    public SecurityService(UserRepositoryInterface repository) {
        this.repository = repository;
    }

    public User save(User user) {
        return this.repository.save(user);
    }

    public List<User> getAll() {
        return this.repository.findAll();
    }

    public User delete(String username) {
        return this.repository.deleteByName(username);
    }

    public User get(String username) {
        return this.repository.findByName(username);
    }
}
