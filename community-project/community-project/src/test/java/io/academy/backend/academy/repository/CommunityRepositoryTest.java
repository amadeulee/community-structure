package io.academy.backend.academy.repository;

import io.academy.backend.academy.entity.Community;
import io.academy.backend.academy.entity.HouseType;
import io.academy.backend.academy.exceptions.NotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CommunityRepositoryTest {

    @Test
    @DisplayName("Given two communities and an empty repository, " +
            "when we save them in repository with save method, " +
            "then repository should have size 2")
    public void saveTwoCommunities() {
        Community c1 = new Community("", "", HouseType.HOUSE);
        Community c2 = new Community("", "", HouseType.HOUSE);

        CommunityRepository repository = new CommunityRepository();
        repository.save(c1);
        repository.save(c2);


        int quantityOfCommunities = repository.get().size();


        assertEquals(2, quantityOfCommunities);
    }

    @Test
    @DisplayName("Given an empty repository, " +
            "when we try to save two communities, but the last one has the same id, " +
            "then it should update the community")
    public void updateCommunity() {
        Community c1 = new Community("", "", HouseType.HOUSE);
        Community c2 = new Community("newC1", "", HouseType.HOUSE);

        CommunityRepository repository = new CommunityRepository();
        repository.save(c1);
        c2.setId(c1.getId());
        repository.save(c2);


        int quantityOfCommunities = repository.get().size();


        assertEquals(1, quantityOfCommunities);
    }


    @Test
    @DisplayName("Given a repository with four communities, " +
            "when we use deleteById method to delete one of them, " +
            "then repository size should decrease by one")
    public void deleteCommunityFromFourCommunitiesList() {
        Community c1 = new Community("", "", HouseType.HOUSE);
        Community c2 = new Community("", "", HouseType.HOUSE);
        Community c3 = new Community("", "", HouseType.HOUSE);
        Community c4 = new Community("", "", HouseType.HOUSE);

        CommunityRepository repository = new CommunityRepository();
        repository.save(c1);
        repository.save(c2);
        repository.save(c3);
        repository.save(c4);
        repository.deleteById("3");


        int quantityOfCommunities = repository.get().size();


        assertEquals(3, quantityOfCommunities);
    }

    @Test
    @DisplayName("Given an empty repository, " +
            "when we use deleteById method to delete one community, " +
            "then it should throw an exception")
    public void deleteCommunityFromEmptyCommunitiesList() {
        CommunityRepository repository = new CommunityRepository();


        assertThrows(NotFoundException.class, () -> repository.deleteById("5"));
    }


    @Test
    @DisplayName("Given a repository with three communities, " +
            "when we use deleteById method to delete a community which isnt inside repository, " +
            "then it should throw an exception")
    public void deleteCommunityFromListWhichHasntThatCommunity() {
        Community c1 = new Community("", "", HouseType.HOUSE);
        Community c2 = new Community("", "", HouseType.HOUSE);
        Community c3 = new Community("", "", HouseType.HOUSE);

        CommunityRepository repository = new CommunityRepository();
        repository.save(c1);
        repository.save(c2);
        repository.save(c3);


        assertThrows(NotFoundException.class, () -> repository.deleteById("10"));
    }


    @Test
    @DisplayName("Given an empty repository, " +
            "when we use get method, " +
            "then we should get an empty array")
    public void getCommunitiesFromEmptyList() {
        CommunityRepository repository = new CommunityRepository();


        int quantityOfCommunities = repository.get().size();


        assertEquals(0, quantityOfCommunities);
    }

    @Test
    @DisplayName("Given a repository with three communities, " +
            "when we use get method, " +
            "then it should return a list with 3 elements")
    public void getCommunitiesFromListOfCommunities() {
        Community c1 = new Community("", "", HouseType.HOUSE);
        Community c2 = new Community("", "", HouseType.HOUSE);
        Community c3 = new Community("", "", HouseType.HOUSE);

        CommunityRepository repository = new CommunityRepository();
        repository.save(c1);
        repository.save(c2);
        repository.save(c3);


        int quantityOfCommunities = repository.get().size();


        assertEquals(3, quantityOfCommunities);
    }

    @Test
    @DisplayName("Given an empty repository, " +
            "when we use findById method, " +
            "then it should return Optional.empty")
    public void findCommunityInEmptyList() {
        CommunityRepository repository = new CommunityRepository();


        Optional<Community> optionalCommunity = repository.findById("1");


        assertEquals(Optional.empty(), optionalCommunity);
    }

    @Test
    @DisplayName("Given a repository with three communities, " +
            "when we use findById to find a community which isnt inside repository, " +
            "then it should return Optional.empty")
    public void findCommunityInListWhichHasntThatCommunity() {
        Community c1 = new Community("", "", HouseType.HOUSE);
        Community c2 = new Community("", "", HouseType.HOUSE);
        Community c3 = new Community("", "", HouseType.HOUSE);

        CommunityRepository repository = new CommunityRepository();
        repository.save(c1);
        repository.save(c2);
        repository.save(c3);


        Optional<Community> optionalCommunity = repository.findById("10");


        assertEquals(Optional.empty(), optionalCommunity);
    }

    @Test
    @DisplayName("Given a repository with three communities, " +
            "when we use findById to find one community, " +
            "then it should return the found community")
    public void findCommunityInListOfCommunities() {
        Community c1 = new Community("", "", HouseType.HOUSE);
        Community c2 = new Community("", "", HouseType.HOUSE);
        Community c3 = new Community("", "", HouseType.HOUSE);

        CommunityRepository repository = new CommunityRepository();
        repository.save(c1);
        repository.save(c2);
        repository.save(c3);


        Optional<Community> foundCommunity = repository.findById("2");


        assertEquals(Optional.of(c2), foundCommunity);
    }

    @Test
    @DisplayName("Given a repository with three communities, " +
            "when we use updateDelete method to delete the third community, " +
            "then repository should have size 2")
    public void softDeleteCommunity() {
        Community c1 = new Community("", "", HouseType.HOUSE);
        Community c2 = new Community("", "", HouseType.HOUSE);
        Community c3 = new Community("", "", HouseType.HOUSE);

        CommunityRepository repository = new CommunityRepository();
        repository.save(c1);
        repository.save(c2);
        repository.save(c3);
        repository.updateDelete("3");


        Integer quantityOfCommunities = (int) repository.get().stream().filter(e -> !e.isDeleted()).count();


        assertEquals(2, quantityOfCommunities);
    }


    @Test
    @DisplayName("Given a repository with three communities, " +
            "when we delete one of them and we use get method with parameters, " +
            "then should return a list with two communities")
    public void getVisibleCommunities() {
        Community c1 = new Community("", "", HouseType.HOUSE);
        Community c2 = new Community("", "", HouseType.HOUSE);
        Community c3 = new Community("", "", HouseType.HOUSE);

        CommunityRepository repository = new CommunityRepository();
        repository.save(c1);
        repository.save(c2);
        repository.save(c3);
        repository.updateDelete("3");


        Integer quantityOfCommunities = repository.get(false).size();


        assertEquals(2, quantityOfCommunities);
    }

    @Test
    @DisplayName("Given a repository with three communities, " +
            "when we delete one of them and we use get method with parameter visibility equals all, " +
            "then should return a list with three elements including deleted items")
    public void getAllCommunitiesIncludesDeleted() {
        Community c1 = new Community("", "", HouseType.HOUSE);
        Community c2 = new Community("", "", HouseType.HOUSE);
        Community c3 = new Community("", "", HouseType.HOUSE);

        CommunityRepository repository = new CommunityRepository();
        repository.save(c1);
        repository.save(c2);
        repository.save(c3);
        repository.updateDelete("3");


        Integer quantityOfCommunities = repository.get(true).size();


        assertEquals(3, quantityOfCommunities);
    }

}