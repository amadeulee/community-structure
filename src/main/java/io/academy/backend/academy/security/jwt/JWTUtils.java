package io.academy.backend.academy.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import java.util.Date;
import org.springframework.stereotype.Component;

@Component
public class JWTUtils {

    public String generateToken(String user, String secret) {
        return JWT.create()
                .withClaim("username", user)
                .withIssuedAt(new Date())
                .sign(Algorithm.HMAC256(secret));
    }

    public String validateToken(String jwtToken, String secret) {
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret))
                    .build();
            DecodedJWT jwt = verifier.verify(jwtToken);
            return jwt.getClaim("username").asString();
        } catch (Exception exception) {
            throw new RuntimeException("Invalid token");
        }
    }

    public String extractTokenAuthorization(String authorization) {
        if(!(authorization == null) && !authorization.isBlank() && !authorization.startsWith("Bearer")) {
            String jwt = authorization.substring(7);

            if(!(jwt == null) && !jwt.isBlank() && jwt.split("\\.").length == 3) {
                return jwt;
            }
        }else throw new RuntimeException("Invalid token in Bearer");
    }
}
