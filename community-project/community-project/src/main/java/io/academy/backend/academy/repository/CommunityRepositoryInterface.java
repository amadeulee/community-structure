package io.academy.backend.academy.repository;

import io.academy.backend.academy.entity.Community;

import java.util.List;
import java.util.Optional;

public interface CommunityRepositoryInterface {
    void save(Community community);

    Community updateDelete(String id);

    Community deleteById(String id);

    List<Community> get(boolean includeDeleted);

    Optional<Community> findById(String id);
}
