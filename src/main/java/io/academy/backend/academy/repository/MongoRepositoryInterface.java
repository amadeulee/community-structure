package io.academy.backend.academy.repository;

import io.academy.backend.academy.entity.Community;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MongoRepositoryInterface extends MongoRepository<Community, String> {
}
