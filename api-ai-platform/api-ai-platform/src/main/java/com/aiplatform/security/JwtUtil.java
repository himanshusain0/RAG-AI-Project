package com.aiplatform.security;

import com.aiplatform.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class JwtUtil {


    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    private SecretKey getKey(){
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String generateToken(User user , List<Long> projectIds){
        Map<String , Object >clains  = new HashMap<>();

        clains.put("userId", user.getId());
        clains.put("fullname" , user.getFullName());
        clains.put("firstName", user.getFirstName());
        clains.put("lastName" , user.getLastName());
        clains.put("email",user.getEmail());
        clains.put("mobile" ,user.getMobile());
        clains.put("userType" , user.getUserType());
        clains.put("loginWay" , "Admin");
        clains.put("projectIds" , projectIds);

        return Jwts.builder()
                .claims(clains)
                .subject(user.getEmail())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis()+expiration))
                .signWith(getKey())
                .compact();
    }
    public Claims extractAllClaims(String token){
        return  Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String extractEmail(String token){
        return  extractAllClaims(token).getSubject();
    }
    public Long extractUserId(String token){
        Object id = extractAllClaims(token).get("userId");
        if(id instanceof Integer)return  ((Integer)id).longValue();
        if(id instanceof Long) return  (Long)id;
        return Long.parseLong(id.toString());
    }

    public String extractLoginWay(String token){
        return  (String) extractAllClaims(token).get("loginWay");
    }

    @SuppressWarnings("unchecked")
    public List<Long> extractProjectIds(String token){
        return  (List<Long>) extractAllClaims(token).get("projectIds");
    }
    public boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }

    public boolean isTokenValid(String token , String email){
        try{
            return  extractEmail(token).equals(email) && !isTokenExpired(token);
        }catch (Exception e){
            return  false;
        }
    }
}


