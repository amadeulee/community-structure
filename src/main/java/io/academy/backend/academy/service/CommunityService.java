package io.academy.backend.academy.service;

import io.academy.backend.academy.controller.SortType;
import io.academy.backend.academy.entity.Community;
import io.academy.backend.academy.entity.HouseType;
import io.academy.backend.academy.exceptions.DivisionByZeroException;
import io.academy.backend.academy.exceptions.EngagementCalculationException;
import io.academy.backend.academy.exceptions.NotFoundException;
import io.academy.backend.academy.exceptions.RepositoryOperationException;
import io.academy.backend.academy.repository.CommunityRepositoryInterface;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommunityService {

    private final CommunityRepositoryInterface repository;

    public CommunityService(CommunityRepositoryInterface repository) {
        this.repository = repository;
    }

    public List<Community> getAll(boolean includeDeleted, SortType sortType) {
        List<Community> communities = repository.get(includeDeleted);

        switch (sortType) {
            case ENGAGEMENT:
                sortByEngagement(communities);

            case NONE:
        }
        return communities;
    }


    public Community getById(String id) {

        return this.repository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Not found community with id: " + id));
    }

    public void deleteById(String id) {
        this.repository.updateDelete(id);
    }

    public void save(Community community) {
        if (community.getId() != null) {
            throw new RepositoryOperationException("Cannot save community witch already have id");
        } else {
            this.repository.save(community);
        }

    }

    public void update(String id, Community community) {
        Community findCommunity = this.repository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Cannot do update operation. Not found community with id: " + id));

        community.setId(id);
        repository.save(community);
    }

    public Community engagement() {
        return engagement(repository.get(true));
    }

    public Community engagement(List<Community> communities) {
        Community engagedCommunity = new Community("", "", HouseType.HOUSE);
        boolean isAllCommunitiesEmpty = true;
        double percentage = 0;

        if (communities.isEmpty()) {
            throw new EngagementCalculationException("The given list hasn't any communities");
        } else {

            for (Community community : communities) {
                try {
                    if (community.activeResidentsPercentage() > percentage
                            || (community.activeResidentsPercentage() == engagedCommunity.activeResidentsPercentage() && community.totalResidents() > engagedCommunity.totalResidents())) {
                        percentage = community.activeResidentsPercentage();
                        engagedCommunity = community;
                    }
                    isAllCommunitiesEmpty = false;
                } catch (DivisionByZeroException e) {
                    System.out.println("The " + community.getName() + " hasnt any residents living there, so you cannot get the percentage of him");
                }
            }
        }
        if (isAllCommunitiesEmpty) {
            throw new EngagementCalculationException("The list of communities has only communities with no residents");
        } else {
            return engagedCommunity;
        }
    }

    private List<Community> sortByEngagement(List<Community> communities) {
        for (int i = 0; i < communities.size(); i++) {
            double fixedPercentage;
            double comparedPercentage;
            try {
                fixedPercentage = communities.get(i).activeResidentsPercentage();
            } catch (DivisionByZeroException exception) {
                System.out.println("The " + communities.get(i).getName() + " hasnt any residents living there, so you cannot get the percentage of him");
                continue;
            }

            for (int j = i + 1; j < communities.size(); j++) {
                try {
                    comparedPercentage = communities.get(j).activeResidentsPercentage();
                } catch (DivisionByZeroException exception) {
                    System.out.println("The " + communities.get(j).getName() + " hasnt any residents living there, so you cannot get the percentage of him");
                    continue;
                }
                if (fixedPercentage < comparedPercentage) {
                    Community aux = communities.get(i);
                    communities.set(i, communities.get(j));
                    communities.set(j, aux);
                }
            }
        }
        return communities;
    }
}
