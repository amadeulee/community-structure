package io.academy.backend.academy.repository;

import io.academy.backend.academy.entity.Community;
import io.academy.backend.academy.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@Profile("MongoDB")
public class MongoRepository implements CommunityRepositoryInterface {

    @Autowired
    MongoRepositoryInterface repositoryInterface;

    @Override
    public void save(Community community) {
        repositoryInterface.save(community);
    }

    @Override
    public Community updateDelete(String id) {
        Community community = repositoryInterface
                .findAll()
                .stream()
                .filter(e -> !e.isDeleted())
                .filter(e -> e.getId().equalsIgnoreCase(id))
                .findFirst().orElseThrow(() -> new NotFoundException("Delete operation failed. Not found community with id: " + id));

        community.setDeleted(!community.isDeleted());
        return repositoryInterface.save(community);
    }

    @Override
    public List<Community> get(boolean includeDeleted) {
        if (includeDeleted) {
            return repositoryInterface.findAll();
        } else {
            return repositoryInterface.findAll()
                    .stream()
                    .filter(e -> !e.isDeleted())
                    .collect(Collectors.toList());
        }
    }

    @Override
    public Optional<Community> findById(String id) {
        return repositoryInterface.findById(id);
    }

    @Override
    public Community deleteById(String id) {
        Community foundCommunity = repositoryInterface
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Delete operation failed. Not found community with id: " + id));

        repositoryInterface.delete(foundCommunity);

        return foundCommunity;
    }
}