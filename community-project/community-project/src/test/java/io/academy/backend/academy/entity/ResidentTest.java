package io.academy.backend.academy.entity;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class ResidentTest {

    @Test
    public void residentHasLoggedIn() {
        Resident resident = new Resident("");
        resident.setLastLoginDate(new Date());


        boolean hasLoggedIn = resident.hasLoggedIn();


        assertTrue(hasLoggedIn);
    }

    @Test
    public void residentHasntLoggedIn() {
        Resident resident = new Resident("");


        boolean hasLoggedIn = resident.hasLoggedIn();


        assertFalse(hasLoggedIn);
    }
}