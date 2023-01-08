package io.academy.backend.academy.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class UnitTest {

    @Test
    public void givenAnUnityWithNoResidentsWhenCountingResidentsTotalThenGetZero() {
        Unit unit = new Unit("");

        unit.addResident(new Resident(""));
        unit.addResident(new Resident(""));


        int loggedResidentsTotal = unit.getActiveResidentCount();


        assertEquals(0, loggedResidentsTotal);
    }

    @Test
    @DisplayName("Given an Unity with three active residents when using getActiveResidentCount method then get 3")
    public void secondActiveResidentsTest() {
        Unit unit = new Unit("");

        Resident firstResident = Mockito.mock(Resident.class);
        Resident secondResident = Mockito.mock(Resident.class);
        Resident thirdResident = Mockito.mock(Resident.class);

        when(firstResident.hasLoggedIn()).thenReturn(true);
        when(secondResident.hasLoggedIn()).thenReturn(true);
        when(thirdResident.hasLoggedIn()).thenReturn(true);

        unit.addResident(firstResident);
        unit.addResident(secondResident);
        unit.addResident(thirdResident);



        int loggedResidentsTotal = unit.getActiveResidentCount();


        assertEquals(3, loggedResidentsTotal);
    }

    @Test
    @DisplayName("Given an Unity with two residents and one of them is active, when using getActiveResidentCount method then get 3")
    public void thirdActiveResidentsTest() {
        Unit unit = new Unit("");

        Resident resident1 = Mockito.mock(Resident.class);
        Resident resident2 = Mockito.mock(Resident.class);

        when(resident1.hasLoggedIn()).thenReturn(true);

        unit.addResident(resident1);
        unit.addResident(resident2);



        int loggedResidentsTotal = unit.getActiveResidentCount();


        assertEquals(1, loggedResidentsTotal);
    }

    @Test
    @DisplayName("Given an Unity with five residents, when we use getResidentCount method then get 5")
    public void firstTotalResidentsTest() {
        Unit unit = new Unit("");

        for (int i = 0; i < 5; i++) {
            unit.addResident(new Resident(""));
        }


        int residentsTotal = unit.getResidentCount();


        assertEquals(5, residentsTotal);
    }

    @Test
    @DisplayName("Given an Unity with no residents, when we use getResidentCount method then get 0")
    public void secondTotalResidentsTest() {
        Unit unit = new Unit("");


        int residentsTotal = unit.getResidentCount();


        assertEquals(0, residentsTotal);
    }
}