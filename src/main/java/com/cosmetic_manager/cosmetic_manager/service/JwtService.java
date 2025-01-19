//package com.cosmetic_manager.cosmetic_manager.service;
//
//import com.cosmetic_manager.cosmetic_manager.dto.UserDto;
//import com.cosmetic_manager.cosmetic_manager.model.User;
//import io.jsonwebtoken.Claims;
//
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import io.jsonwebtoken.io.Decoders;
//import io.jsonwebtoken.security.Keys;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//
//import java.security.Key;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.function.Function;
//
//@Service
//public class JwtService {
//    @Value("${security.jwt.secret-key}")
//    private String secret;
//
//    @Value("${security.jwt.expiration-time}")
//    private long expirationTime;
//
//    public String extractEmail(String token) {
//        return extractClaim(token, Claims::getSubject);
//    }
//
//    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
//        final Claims claims = extractAllClaims(token);
//        return claimsResolver.apply(claims);
//    }
//
//    public String generateToken(User user) {
//        return generateToken(new HashMap<>(), user);
//    }
//
//    public String generateToken(Map<String, Object> extraClaims, User user) {
//        return buildToken(extraClaims, user, expirationTime);
//    }
//
//    private String buildToken(Map<String, Object> extraClaims, User user, long expirationTime) {
//        return Jwts.builder()
//                .setClaims(extraClaims)
//                .setSubject(user.getEmail())
//                .setIssuedAt(new Date(System.currentTimeMillis()))
//                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
//                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
//                .compact();
//    }
//
//    public Date extractExpiration(String token) {
//        return extractClaim(token, Claims::getExpiration);
//    }
//
//    public boolean isTokenValid(String token, User user) {
//        final String email = extractEmail(token);
//        return email.equals(user.getEmail()) && !isTokenExpired(token);
//    }
//
//    public boolean isTokenExpired(String token) {
//        return extractExpiration(token).before(new Date());
//    }
//
//    private Key getSignInKey() {
//        byte[] keyBytes = Decoders.BASE64.decode(secret);
//        return Keys.hmacShaKeyFor(keyBytes);
//    }
//
//    private Claims extractAllClaims(String token) {
//        return Jwts
//                .parserBuilder()
//                .setSigningKey(getSignInKey())
//                .build()
//                .parseClaimsJws(token)
//                .getBody();
//    }
//
//    public long getExpirationTime() {
//        return expirationTime;
//    }
//}
