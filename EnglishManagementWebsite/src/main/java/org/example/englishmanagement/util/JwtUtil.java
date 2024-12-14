package org.example.englishmanagement.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    private static final Key SIGNING_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    private static final JwtUtil INSTANCE = new JwtUtil();

    private JwtUtil() {}

    public static JwtUtil getInstance() {
        return INSTANCE;
    }

    public String generateToken(String userId, String roleId) {
        return Jwts.builder()
                .setSubject(userId)
                .claim("roleId", roleId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(SIGNING_KEY)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(SIGNING_KEY)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static String extractCode(String token) {
        String tokenWithoutBearer = token.replace("Bearer ", "");
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(SIGNING_KEY)
                .build()
                .parseClaimsJws(tokenWithoutBearer)
                .getBody();
        return claims.getSubject();
    }

    public static String extractRoleId(String token) {
        String tokenWithoutBearer = token.replace("Bearer ", "");
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(SIGNING_KEY)
                .build()
                .parseClaimsJws(tokenWithoutBearer)
                .getBody();
        return claims.get("roleId", String.class);
    }

    public static void validateAdminAccess(String token) {
        String roleId = extractRoleId(token);
        if (!"admin".equals(roleId)) {
            throw new RuntimeException("Access denied: User is not an admin.");
        }
    }

    public static void validateTeacherAccess(String token) {
        String roleId = extractRoleId(token);
        if (!"teacher".equals(roleId)) {
            throw new RuntimeException("Access denied: User is not an teacher.");
        }
    }

    public static void validateStudentAccess(String token) {
        String roleId = extractRoleId(token);
        if (!"student".equals(roleId)) {
            throw new RuntimeException("Access denied: User is not an teacher.");
        }
    }
}
