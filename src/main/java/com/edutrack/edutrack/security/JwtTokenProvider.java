/**
 * @Author: Jobet P. Casquejo
 * @Date: 2025-31-8
 * @Version: 1.0
 * @Description: JWT token provider for user authentication and authorization.
 */
package com.edutrack.edutrack.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

/**
 * Utility class for generating and validating JWT tokens.
 * <p>
 * This class handles creating JWT tokens, extracting username from tokens,
 * and validating tokens for authentication purposes.
 * </p>
 */
@Component
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration-ms}")
    private long jwtExpirationMs;

    /**
     * Generates the signing key from the JWT secret.
     *
     * @return a Key object for signing JWTs
     */
    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }

    /**
     * Generates a JWT token for a given username.
     *
     * @param username the username to include in the token
     * @return a signed JWT token as a String
     */
    public String generateToken(String username) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationMs);

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Extracts the username from a JWT token.
     *
     * @param token the JWT token
     * @return the username included in the token
     */
    public String extractUsername(String token) {
        return parseClaims(token).getSubject();
    }

    /**
     * Validates the JWT token.
     *
     * @param token the JWT token
     * @param userDetails the user details object (not used in this implementation)
     * @return true if the token is valid and not expired, false otherwise
     */
    public boolean validateToken(String token, Object userDetails) {
        try {
            Claims claims = parseClaims(token);
            return !claims.getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Parses the claims from a JWT token.
     *
     * @param token the JWT token
     * @return the Claims object extracted from the token
     */
    private Claims parseClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
