package com.example.visitor_crm_be.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtHelper {

    private final String secret = "39e314111aea87e5bb29df023ed76c964978f87d20e2118bf97e872167b398cbf0578b97c744f3d2e54cd9a8ec7905e07b8e7122476b5dbbc5916aa117ce272a4115d719f81b41cda294128c5e760518644adeafd3cfc4a7fbff60f242e44b32cca21ed81764d1855f80fcc8a910041a49730b39d085ab2be7c56352ad27c90c98afe27991228054a45f4dc0461b55321d1fe9c0cee168e66f887f4197c6072081bd56085a3d718bce34014e5f4f4ed88897c4f10f2ee77ffa6d0d72683431dc1154da7e18e38a9f286d4341866bb2cf138815ff480dba83596e9dd782393002fd672acf0abf62ede35a57218b081d7cd325413508545abf3fb4d7dedabc91c2";
    private final long jwtTokenValidity = 36000000;


    // Retrieve username from jwt token
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, claims -> claims.get("username", String.class));
    } // user to get name

    // Retrieve expiration date from jwt token
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    } // get Expiration Date from token

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {     // i d k
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    // For retrieving any information from token we will need the secret key
    public Claims getAllClaimsFromToken(String token) {                                    // i d k
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    // Check if the token has expired
    private Boolean isTokenExpired(String token) {                                      // checking expire
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    // Generate token for user
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", List.of(new SimpleGrantedAuthority(userDetails.getAuthorities().iterator().next().getAuthority()).toString()));
        claims.put("username", userDetails.getUsername());
        return doGenerateToken(claims, userDetails);
    }

    // While creating the token -
    // 1. Define claims of the token, like Issuer, Expiration, Subject, and the ID
    // 2. Sign the JWT using the HS512 algorithm and secret key
    // 3. According to JWS Compact Serialization(https://tools.ietf.org/html/draft-ietf-jose-json-web-signature-41#section-3.1)
    //    compaction of the JWT to a URL-safe string
    private String doGenerateToken(Map<String, Object> claims, UserDetails userDetails) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtTokenValidity * 1000))
                .signWith(SignatureAlgorithm.HS512, secret).compact();
    }

    // Validate token
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}