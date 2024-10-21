package com.kihong.chatserver.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Component
public class JwtUtil {

    private static final String SECRET_KEY = "GOOD-LUCK-KIHONG";

    public static String generateToken(String username, String id) {
        return JWT.create()
                .withExpiresAt(Instant.now().plus(14, ChronoUnit.DAYS))
                .withSubject(id)
                .withClaim("username", username)
                .sign(Algorithm.HMAC256(SECRET_KEY));
    }

    public static DecodedJWT verifyToken(String token) {
        return JWT.require(Algorithm.HMAC256(SECRET_KEY)).build().verify(token);
    }

}
