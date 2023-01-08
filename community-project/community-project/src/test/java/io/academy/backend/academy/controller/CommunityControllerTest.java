package io.academy.backend.academy.controller;

import io.academy.backend.academy.entity.Community;
import io.academy.backend.academy.entity.HouseType;
import io.academy.backend.academy.exceptions.NotFoundException;
import io.academy.backend.academy.service.CommunityService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class CommunityControllerTest {

    @Test
    @DisplayName("Given a service with sorted by engagement communities, " +
            "when we use controller getAll method passing ENGAGEMENT as parameter, " +
            "then the first community should have the bigger percentage of active residents")
    public void getAllUsingEngagementAsArgument() {
        Community c1 = Mockito.mock(Community.class);
        Community c2 = Mockito.mock(Community.class);
        Community c3 = Mockito.mock(Community.class);
        when(c1.activeResidentsPercentage()).thenReturn(0.8);
        when(c2.activeResidentsPercentage()).thenReturn(0.9);
        when(c3.activeResidentsPercentage()).thenReturn(0.6);

        CommunityService service = Mockito.mock(CommunityService.class);
        when(service.getAll(true, SortType.ENGAGEMENT)).thenReturn(Arrays.asList(c2, c1, c3));

        CommunityController controller = new CommunityController(service);


        assertEquals(0.9, controller.getAll(true, SortType.ENGAGEMENT).get(0).activeResidentsPercentage());
    }

    @Test
    @DisplayName("Given a service with an element with id 1, " +
            "when we use controller get method passing 1 as argument, " +
            "than should find the service element with id 1")
    public void getMethodUsingIdTest() {
        CommunityService service = Mockito.mock(CommunityService.class);
        CommunityController controller = new CommunityController(service);
        Community c1 = new Community("", "", HouseType.HOUSE);

        when(service.getById("1")).thenReturn(c1);


        assertEquals(c1, controller.get("1"));
    }

    @Test
    @DisplayName("Given an empty repository, " +
            "when we use service method to find the first community, " +
            "then should throw NotFoundException")
    public void getByIdTestWithNonExistCommunity() {
        CommunityService service = Mockito.mock(CommunityService.class);
        when(service.getById("1")).thenThrow((NotFoundException.class));

        CommunityController controller = new CommunityController(service);


        assertThrows(NotFoundException.class, () -> controller.get("1"));
    }

    @Test
    @DisplayName("Given a repository with three communities, " +
            "when we use getAll method passing NONE for SortType, " +
            "then it should return the list with all three communities")
    public void getAllCommunities() {
        CommunityService service = Mockito.mock(CommunityService.class);
        when(service.getAll(true, SortType.NONE))
                .thenReturn(Arrays.asList(Mockito.mock(Community.class), Mockito.mock(Community.class), Mockito.mock(Community.class)));

        CommunityController controller = new CommunityController(service);


        assertEquals(3, controller.getAll(true, SortType.NONE).size());
    }
}