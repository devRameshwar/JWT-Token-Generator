package com.jwt.security.jwtToken;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import static io.jsonwebtoken.Jwts.builder;
import static io.jsonwebtoken.Jwts.parser;

@Component
public class ApplicationTokenGeneretor {

    private final String secreteKey = "XXVYWEBBING1NI19LO0JQW1TYBVBVF8HVAC8ADHHGGGJH";

    /*TODO: generating a token */
    public String getJwtToken(String UserId) {

        return builder()
                .setSubject(UserId)
                .setIssuer("ICICI Bank")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(2)))
                .signWith(SignatureAlgorithm.HS256, secreteKey).encodePayload(true).compact();

    }

    //TODO: getting the username form exiting token
    public String getUserNameFromToken(String token) {

        String user = parser()
                .setSigningKey(secreteKey)
                .build().parseSignedClaims(token)
                .getPayload()
                .getSubject();
        return user;

        /*Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
        in this code parseClaimsJws(token) method is decrypted replace with
         .build().parseSignedClaims(token)*/
    }

    //TODO: Create a method with a Boolean return type for check token is valid or not
    public Boolean getTokenVailedity(String token) {
        try {
            Date expiration = parser()
                    .setSigningKey(secreteKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .getExpiration();
            System.err.println("expiration: " + expiration);

            Date issuedAt = parser()
                    .setSigningKey(secreteKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .getIssuedAt();
            System.out.println("issuedAt: " + issuedAt);
            //it returns every time false if token is availed if token is invalided then it throws exception
            return !expiration.before(issuedAt);

        } catch (ExpiredJwtException e) {
            return false;
        }
    }

    /*TODO: to check the token is vailed or note with help of request user name and token*/
    public Boolean isTokenVailed(String requestUserName, String token) {
        /*Here we check Two things
         * 1. token time interval= true and
         * 2. Check UserName match or not from token */
        Boolean tokenVailedity = this.getTokenVailedity(token);
        String userNameFromToken = this.getUserNameFromToken(token);
        return userNameFromToken.equals(requestUserName) & tokenVailedity;
    }
}
