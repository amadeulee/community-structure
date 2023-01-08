package io.academy.backend.academy.entity;

import io.academy.backend.academy.exceptions.DivisionByZeroException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.when;

class CommunityTest {

    @Test
    @DisplayName("Given an empty community when we use getActiveResidentsPercentage method then get an Exception")
    public void emptyCommunity() {
        Community community = new Community("", "", HouseType.APARTMENT);
        community.setUnit(new Unit(""));


        assertThrows(DivisionByZeroException.class, community::activeResidentsPercentage);
    }

    @Test
    @DisplayName("Given a community with full residents active, when we use getActiveResidentsPercentage method then get 100%")
    public void fullCommunity() {
        Community community = new Community("", "", HouseType.APARTMENT);
        Unit unit1 = Mockito.mock(Unit.class);
        Unit unit2 = Mockito.mock(Unit.class);

        when(unit1.getResidentCount()).thenReturn(2);
        when(unit2.getResidentCount()).thenReturn(2);
        when(unit1.getActiveResidentCount()).thenReturn(2);
        when(unit2.getActiveResidentCount()).thenReturn(2);

        community.setUnit(unit1);
        community.setUnit(unit2);


        double percentage = community.activeResidentsPercentage();


        assertEquals(100, percentage);
    }

    @Test
    @DisplayName("Given a community with half of residents active, when we use getActiveResidentsPercentage method then get 50%")
    public void communityHalfFull() {
        Community community = new Community("", "", HouseType.HOUSE);
        Unit unit1 = Mockito.mock(Unit.class);
        Unit unit2 = Mockito.mock(Unit.class);

        when(unit1.getResidentCount()).thenReturn(2);
        when(unit2.getResidentCount()).thenReturn(2);
        when(unit1.getActiveResidentCount()).thenReturn(2);
        when(unit2.getActiveResidentCount()).thenReturn(0);

        community.setUnit(unit1);
        community.setUnit(unit2);


        double percentage = community.activeResidentsPercentage();


        assertEquals(50, percentage);
    }

    @Test
    @DisplayName("Given a community with one tenth of residents active, when we use getActiveResidentsPercentage method then get 10%")
    public void tenthOfCommunity() {
        Community community = new Community("", "", HouseType.HOUSE);
        Unit unit1 = Mockito.mock(Unit.class);
        Unit unit2 = Mockito.mock(Unit.class);

        when(unit1.getResidentCount()).thenReturn(50);
        when(unit2.getResidentCount()).thenReturn(50);
        when(unit1.getActiveResidentCount()).thenReturn(7);
        when(unit2.getActiveResidentCount()).thenReturn(3);

        community.setUnit(unit1);
        community.setUnit(unit2);


        double percentage = community.activeResidentsPercentage();


        assertEquals(10, percentage);
    }
}