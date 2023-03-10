package io.academy.backend.academy.service;

import io.academy.backend.academy.controller.SortType;
import io.academy.backend.academy.entity.Community;
import io.academy.backend.academy.exceptions.DivisionByZeroException;
import io.academy.backend.academy.exceptions.EngagementCalculationException;
import io.academy.backend.academy.exceptions.NotFoundException;
import io.academy.backend.academy.exceptions.RepositoryOperationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class CommunityServiceTest {

    @Test
    @DisplayName("Given three communities, when we use engagement method, then we should get the community with higher percentage of active residents")
    public void topEngagedCommunity() {
        Community community1 = Mockito.mock(Community.class);
        Community community2 = Mockito.mock(Community.class);
        Community community3 = Mockito.mock(Community.class);

        when(community1.activeResidentsPercentage()).thenReturn(50.0);
        when(community2.activeResidentsPercentage()).thenReturn(80.0);
        when(community3.activeResidentsPercentage()).thenReturn(60.0);

        CommunityRepository repository = Mockito.mock(CommunityRepository.class);
        when(repository.get(true)).thenReturn(Arrays.asList(community2, community3, community1));
        CommunityService service = new CommunityService(repository);


        Community topEngagedCommunity = service.engagement();


        assertEquals(community2, topEngagedCommunity);
    }


    @Test
    @DisplayName("Given three communities which two of them has the same and higher percentage of active residents and the same number of residents, " +
            "when we use engagement method, " +
            "then we should get the first one")
    public void communitiesWithSameEngagementAndSameResidents() {
        Community community1 = Mockito.mock(Community.class);
        Community community2 = Mockito.mock(Community.class);
        Community community3 = Mockito.mock(Community.class);

        when(community1.activeResidentsPercentage()).thenReturn(85.0);
        when(community2.activeResidentsPercentage()).thenReturn(45.0);
        when(community3.activeResidentsPercentage()).thenReturn(85.0);
        when(community1.totalResidents()).thenReturn(20);
        when(community2.totalResidents()).thenReturn(40);
        when(community3.totalResidents()).thenReturn(20);

        CommunityRepository repository = Mockito.mock(CommunityRepository.class);
        when(repository.get(true)).thenReturn(Arrays.asList(community1, community2, community3));
        CommunityService service = new CommunityService(repository);


        Community topEngagedCommunity = service.engagement();


        assertEquals(community1, topEngagedCommunity);
    }

    @Test
    @DisplayName("Given three communities which two of them has the same and higher percentage of active residents, " +
            "when we use engagement method, " +
            "then we should get the community with more total residents")
    public void tieBetweenEngagementOfCommunities() {
        Community community1 = Mockito.mock(Community.class);
        Community community2 = Mockito.mock(Community.class);
        Community community3 = Mockito.mock(Community.class);

        when(community1.activeResidentsPercentage()).thenReturn(85.0);
        when(community2.activeResidentsPercentage()).thenReturn(45.0);
        when(community3.activeResidentsPercentage()).thenReturn(85.0);
        when(community1.totalResidents()).thenReturn(10);
        when(community2.totalResidents()).thenReturn(100);
        when(community3.totalResidents()).thenReturn(20);

        CommunityRepository repository = Mockito.mock(CommunityRepository.class);
        when(repository.get(true)).thenReturn(Arrays.asList(community2, community3, community1));
        CommunityService service = new CommunityService(repository);


        Community topEngagedCommunity = service.engagement();


        assertEquals(community3, topEngagedCommunity);
    }

    @Test
    @DisplayName("Given two communities without any residents, " +
            "when we use engagement method, " +
            "then it should throw an exception ")
    public void allCommunitiesWithNoResidents() {
        Community community1 = Mockito.mock(Community.class);
        Community community2 = Mockito.mock(Community.class);
        when(community1.activeResidentsPercentage()).thenThrow(DivisionByZeroException.class);
        when(community2.activeResidentsPercentage()).thenThrow(DivisionByZeroException.class);
        when(community1.getName()).thenReturn("Vale do Silicio");
        when(community2.getName()).thenReturn("Vale das pitangas");

        CommunityRepository repository = Mockito.mock(CommunityRepository.class);
        when(repository.get(true)).thenReturn(Arrays.asList(community2, community1));
        CommunityService service = new CommunityService(repository);


        assertThrows(EngagementCalculationException.class, service::engagement);
    }

    @Test
    @DisplayName("Given an empty list of communities, " +
            "when we use engagement method, " +
            "then it should throw an exception")
    public void emptyCommunitieList() {
        CommunityRepository repository = Mockito.mock(CommunityRepository.class);
        CommunityService service = new CommunityService(repository);


        assertThrows(EngagementCalculationException.class, service::engagement);
    }

    @Test
    @DisplayName("Given four communities which two of them has no residents, " +
            "when we use engagement method, " +
            "then we should get the community with higher percentage of active residents")
    public void communitieListWithSomeEmptyCommunities() {
        Community community1 = Mockito.mock(Community.class);
        Community community2 = Mockito.mock(Community.class);
        Community community3 = Mockito.mock(Community.class);
        Community community4 = Mockito.mock(Community.class);

        when(community1.activeResidentsPercentage()).thenThrow(DivisionByZeroException.class);
        when(community2.activeResidentsPercentage()).thenThrow(DivisionByZeroException.class);
        when(community1.getName()).thenReturn("Vale do Silicio");
        when(community2.getName()).thenReturn("Vale das pitangas");
        when(community3.activeResidentsPercentage()).thenReturn(45.0);
        when(community4.activeResidentsPercentage()).thenReturn(45.0);
        when(community3.totalResidents()).thenReturn(100);
        when(community4.totalResidents()).thenReturn(200);

        CommunityRepository repository = Mockito.mock(CommunityRepository.class);
        when(repository.get(true)).thenReturn(Arrays.asList(community2, community3, community1, community4));
        CommunityService service = new CommunityService(repository);


        Community topEngagedCommunity = service.engagement();


        assertEquals(community4, topEngagedCommunity);
    }

    @Test
    public void givenEmptyList_whenWeSaveTwoCommunities_thenListShouldHaveSizeTwo() {
        CommunityRepository repository = Mockito.mock(CommunityRepository.class);
        when(repository.get(false)).thenReturn(Arrays.asList(Mockito.mock(Community.class), Mockito.mock(Community.class)));
        CommunityService service = new CommunityService(repository);


        assertEquals(2, service.getAll(false, SortType.NONE).size());
    }

    @Test
    public void givenCommunitiesList_whenUseGetAllMethodWithSortByEngagementParameter_thenReturnSortedList() {
        CommunityRepository repository = Mockito.mock(CommunityRepository.class);
        Community c1 = Mockito.mock(Community.class);
        Community c2 = Mockito.mock(Community.class);
        Community c3 = Mockito.mock(Community.class);

        when(c1.activeResidentsPercentage()).thenReturn(0.8);
        when(c2.activeResidentsPercentage()).thenReturn(0.9);
        when(c3.activeResidentsPercentage()).thenReturn(0.6);
        when(c1.isDeleted()).thenReturn(true);
        when(c2.isDeleted()).thenReturn(false);
        when(c3.isDeleted()).thenReturn(false);
        when(repository.get(true)).thenReturn(Arrays.asList(c2, c3));

        CommunityService service = new CommunityService(repository);


        assertEquals(0.9, service.getAll(true, SortType.ENGAGEMENT).get(0).activeResidentsPercentage());
    }

    @Test
    public void givenListWithInactivesCommunities_whenUsingGetAllMethodWithVisibilityParameter_thenReturnListOfActivesCommunities() {
        Community c1 = Mockito.mock(Community.class);
        Community c2 = Mockito.mock(Community.class);
        Community c3 = Mockito.mock(Community.class);
        Community c4 = Mockito.mock(Community.class);
        Community c5 = Mockito.mock(Community.class);

        when(c1.isDeleted()).thenReturn(true);
        when(c2.isDeleted()).thenReturn(false);
        when(c3.isDeleted()).thenReturn(false);
        when(c4.isDeleted()).thenReturn(true);
        when(c5.isDeleted()).thenReturn(false);

        CommunityRepository repository = Mockito.mock(CommunityRepository.class);
        when(repository.get(false)).thenReturn(Arrays.asList(c2, c3, c5));
        CommunityService service = new CommunityService(repository);


        assertEquals(3, service.getAll(false, SortType.NONE).size());
    }

    @Test
    public void givenCommunitiesList_whenUsingGetByIdMethod_thenReturnFoundCommunity() {
        Community c1 = Mockito.mock(Community.class);
        when(c1.getId()).thenReturn("1");

        CommunityRepository repository = Mockito.mock(CommunityRepository.class);
        when(repository.findById("1")).thenReturn(Optional.of(c1));

        CommunityService service = new CommunityService(repository);


        Community foundCommunity = service.getById("1");


        assertEquals(c1, foundCommunity);
    }

    @Test
    public void givenCommunitiesList_whenUsingGetByIdMethodAndCommunityIsntInside_thenThrowException() {
        CommunityRepository repository = Mockito.mock(CommunityRepository.class);
        when(repository.findById("1")).thenThrow(NotFoundException.class);

        CommunityService service = new CommunityService(repository);


        assertThrows(NotFoundException.class, () -> service.getById("1"));
    }

    @Test
    public void givenCommunityWithId_whenUsingSaveMethod_thenThrowException() {
        Community c1 = Mockito.mock(Community.class);
        when(c1.getId()).thenReturn("1");

        CommunityRepository repository = Mockito.mock(CommunityRepository.class);
        CommunityService service = new CommunityService(repository);


        assertThrows(RepositoryOperationException.class, () -> service.save(c1));
    }


    @Test
    public void givenCommunityListWithThreeCommunities_whenUsingDeleteByIdWithNonExistId_thenThrowException() {
        CommunityRepository repository = Mockito.mock(CommunityRepository.class);
        when(repository.updateDelete("5")).thenThrow(NotFoundException.class);
        CommunityService service = new CommunityService(repository);


        assertThrows(NotFoundException.class, () -> service.deleteById("5"));
    }
}