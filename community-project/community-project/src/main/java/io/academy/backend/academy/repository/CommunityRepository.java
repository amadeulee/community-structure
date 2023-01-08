package io.academy.backend.academy.repository;

import io.academy.backend.academy.entity.Community;
import io.academy.backend.academy.exceptions.NotFoundException;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@Profile("NoDB")
public class CommunityRepository implements CommunityRepositoryInterface {
    List<Community> communities;
    private int count;

    public CommunityRepository() {
        this.communities = new ArrayList<>();
        this.count = 1;
    }

    private int findIndexOfCommunity(String id) {

        return this.communities
                .stream()
                .filter(e -> !e.isDeleted())
                .map(e -> e.getId().toLowerCase())
                .collect(Collectors.toList())
                .indexOf(id.toLowerCase());
    }

    public void save(Community community) {
        int indexOfCommunity = community.getId() != null ? this.findIndexOfCommunity(community.getId()) : -1;

        if (indexOfCommunity == -1) {
            community.setId(String.valueOf(this.count));
            this.communities.add(community);
            this.count++;
        } else {
            this.communities.set(indexOfCommunity, community);
        }
    }

    public Community deleteById(String id) {
        int indexOfCommunity = this.findIndexOfCommunity(id);

        if (indexOfCommunity == -1) {
            throw new NotFoundException("Delete operation failed. Not found community with id: " + id);
        } else {
            return this.communities.remove(indexOfCommunity);
        }
    }

    public Community updateDelete(String id) {
        int indexOfCommunity = this.findIndexOfCommunity(id);

        if (indexOfCommunity == -1) {
            throw new NotFoundException("Delete operation failed. Not found community with id: " + id);
        } else {
            this.communities.get(indexOfCommunity).setDeleted(true);
            return this.communities.get(indexOfCommunity);
        }
    }

    public Optional<Community> findById(String id) {
        return this.communities
                .stream()
                .filter(e -> !e.isDeleted())
                .filter(e -> e.getId().equalsIgnoreCase(id))
                .findFirst();
    }

    public List<Community> get() {
        return this.communities;
    }

    public List<Community> get(boolean includeDeleted) {
        if (includeDeleted) {
            return new ArrayList<>(this.communities);
        } else {
            return this.communities
                    .stream()
                    .filter(e -> !e.isDeleted())
                    .collect(Collectors.toList());
        }
    }


}