package com.cs203.TicketWarrior.Registration.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    private static final String SECRET_KEY = "45E6A8374CF4C81918AE5A8886BEE";

    public String extractUsername(String token) {
        return extractClaims(token, Claims::getSubject);
    }

    //Obtain a specific claim
    public <C> C extractClaims(String token, Function<Claims, C> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // Create a new token based on user info and extra claims
    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24)) //Expire in 24hrs
                .signWith(getSignInKey(), SignatureAlgorithm.HS256) //Set signing key, we are using 256 bits
                .compact(); //build the token
    }

    //Obtain all claims
    private Claims extractAllClaims(String token) {

        // Build a Jwts object and pass it our token to extract the Claims
        // Signing key is used to digitially sign the token for security purposes
        return Jwts.parserBuilder().setSigningKey(getSignInKey()).build().parseClaimsJws(token).getBody();
    }

    //Generate signing key
    private Key getSignInKey() {
        // Use the secret key string that was generated, split into bytes and generate a key
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
