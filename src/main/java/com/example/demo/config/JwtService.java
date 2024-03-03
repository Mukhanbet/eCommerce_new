package com.example.demo.config;

import com.example.demo.exception.BadCredentialsException;
import com.example.demo.exception.BadRequestException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class JwtService {

    @Value("${application.security.jwt.secret-key}")
    private String secretKey;
    @Value("${application.security.jwt.expiration}")
    private Long jwtLifeTime;

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();

        String role = userDetails.getAuthorities().toString();
        claims.put("role", role);

        Date issuedDate = new Date();
        Date expireDate = new Date(issuedDate.getTime() + jwtLifeTime);
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(issuedDate)
                .setExpiration(expireDate)
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String getUserName(String token) {
        return getAllClaimsFromToken(token).getSubject();
    }

    public String getRole(String token) {
        return getAllClaimsFromToken(token).get("role", String.class);
    }

    private Claims getAllClaimsFromToken(String token) throws ExpiredJwtException {
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey()) // Когда мы что-то хотим получить из токена, мы указываем ключ. Если кто-то подменять данные то мы в этом этапе получим ощибку
                .build()

                // вот здесь вот сама библиотека проверяет а
                //корректная ли подпись до проверяет по секрету а не вышел ли срок жизни у токена а Имеет ли он правильную
                //структуру вот здесь много много проверок зашито нам их самим делать не надо мы на них только реагируем
                .parseClaimsJws(token)
                .getBody();
    }
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = getUserName(token);
        Date expiration = getAllClaimsFromToken(token).getExpiration();
        return (username.equals(userDetails.getUsername())) && expiration.after(new Date());
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
