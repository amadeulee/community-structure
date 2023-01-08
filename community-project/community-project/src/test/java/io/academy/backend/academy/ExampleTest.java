package io.academy.backend.academy;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ExampleTest {

    @Test
    public void trueTest() {
        Example example = new Example();

        boolean result = example.t();

        assertTrue(result);
    }

}