package me.nos.jwtgraphqljava.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtUtil {
    //    @Value("${jwt.secret}")
    private final String secret = "my-secret";
    private final Algorithm algorithm = Algorithm.HMAC256(secret);

    public String generateToken(@NotNull UserDetails userDetails) throws IllegalArgumentException, JWTCreationException {

        String[] claims = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toArray(String[]::new);

        int validPeriod = 1000 * 60 * 60 * 10; // 10 hours

        return JWT.create()
                .withSubject(userDetails.getUsername())
                .withArrayClaim("roles", claims)
                .withIssuer("nasos")
                .withIssuedAt(new Date(System.currentTimeMillis()))
                .withExpiresAt(new Date(System.currentTimeMillis() + validPeriod))
                .sign(algorithm);
    }

    public Boolean validateToken(String token, @NotNull UserDetails userDetails) throws JWTVerificationException {
        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer("nasos")
                .build();
        DecodedJWT jwt = verifier.verify(token);
        return jwt.getSubject().equals(userDetails.getUsername());
    }

    public String getUsername(String token) throws JWTDecodeException {
        return JWT.decode(token).getSubject();
    }
}
