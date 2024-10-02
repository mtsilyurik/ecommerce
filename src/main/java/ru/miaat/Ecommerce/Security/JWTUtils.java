package ru.miaat.Ecommerce.Security;

import io.jsonwebtoken.Claims;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ru.miaat.Ecommerce.Entity.User;

import io.jsonwebtoken.Jwts;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.function.Function;

@Service
@Slf4j
public class JWTUtils {

    private static final long EXPIRATION_TIME_MILLISEC = 1000L * 60L * 60L * 24L * 30L * 6L;

    private SecretKey secretKey;

    @Value("${secureJWTString}")
    private String secureString;

    @PostConstruct
    private void init() {
        //Init secretKey after building the bean
        byte[] keyBytes = secureString.getBytes(StandardCharsets.UTF_8);
        this.secretKey = new SecretKeySpec(keyBytes, "HmacSHA256");
    }


    // Generating token
    public String createToken(User user) {
        String userEmail = user.getEmail();
        return generateToken(userEmail);
    }

    public String generateToken(String email) {
        return Jwts.builder()
                .subject(email)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME_MILLISEC))
                .signWith(secretKey)
                .compact();
    }

    public String getUsernameFromToken(String token) {
        return extractClaims(token, Claims::getSubject);
    }

    private <T> T extractClaims(String token, Function<Claims, T> claimsResolver) {
        return claimsResolver.apply(Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload());
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }


    private boolean isTokenExpired(String token) {
        return extractClaims(token, Claims::getExpiration).before(new Date());
    }
}
