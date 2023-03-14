package io.academy.backend.academy.security.jwt;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class JWTUtilsTest {

    @Test
    public void testJwtUtils() {
        JWTUtils jwtUtils = new JWTUtils();
        String username = "amadeu";
        String secret = "secret";

        String jwt_token = jwtUtils.generateToken(username, secret);

        System.out.println(jwt_token);


    }
}