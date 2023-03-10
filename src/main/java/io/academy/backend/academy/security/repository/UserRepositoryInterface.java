package io.academy.backend.academy.security.repository;

import io.academy.backend.academy.security.entity.User;
import java.util.UUID;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepositoryInterface extends MongoRepository<User, Integer> {
    User deleteByName(String name);
    User findByName(String name);
}
