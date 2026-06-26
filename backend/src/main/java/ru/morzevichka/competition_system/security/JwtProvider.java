package ru.morzevichka.competition_system.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.morzevichka.competition_system.exception.auth.JwtTypeException;
import ru.morzevichka.competition_system.model.User;
import ru.morzevichka.competition_system.model.UserRole;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JwtProvider {

    private final JwtProperties properties;

    private SecretKey key;

    @PostConstruct
    void setKey() {
        byte[] keyBytes = Decoders.BASE64.decode(properties.getSecretKey());
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public UUID extractUuid(String token) {
        return extractClaim(token, claims -> UUID.fromString(claims.get("uid").toString()));
    }

    public UserRole extractRole(String token) {
        return extractClaim(token, claims -> UserRole.valueOf(claims.get("role").toString()));
    }

    public TokenType extractTokenType(String token) {
        return extractClaim(token, claims -> TokenType.valueOf(claims.get("type").toString()));
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) throws JwtException {
        return Jwts
                .parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String generateToken(User user) {
        HashMap<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("uid", user.getId());
        extraClaims.put("role", user.getRole().name());
        return generateToken(extraClaims, user.getEmail());
    }

    private String generateToken(Map<String, Object> extraClaims, String username) {
        return buildToken(extraClaims, username, properties.getAccessExpiration(), TokenType.ACCESS);
    }

    public String generateRefreshToken(String username) {
        return buildToken(new HashMap<>(), username, properties.getRefreshExpiration(), TokenType.REFRESH);
    }

    private String buildToken(Map<String, Object> extraClaims, String username, Long expiration, TokenType type) {
        extraClaims.put("type", type);

        return Jwts
                .builder()
                .header()
                    .add("typ", "JWT")
                .and()
                .claims(extraClaims)
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(key, Jwts.SIG.HS256)
                .compact();
    }

    public void isTokenValid(String token) {
        if (!extractTokenType(token).equals(TokenType.ACCESS)) {
            throw new JwtTypeException("Invalid JWT Token Type");
        }
    }

    public void isRefreshTokenValid(String token) {
        if (!extractTokenType(token).equals(TokenType.REFRESH)) {
            throw new JwtTypeException("Invalid JWT Token Type");
        }
    }
}
