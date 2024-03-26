package com.jwt.jwtToken;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import static io.jsonwebtoken.Jwts.*;

@Component
public class ApplicationTokenGeneretor {

    private String secreteKey = "XXVYWEBBING1NI19LO0JQW1TYBVBVF8HVAC8ADHHGGGJH";

    public String getJwtToken(String UserId) {

        return builder()
                .setSubject(UserId)
                .setIssuer("ICICI Bank")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(2)))
                .signWith(SignatureAlgorithm.HS256,secreteKey)
                .encodePayload(true)
                .compact();

    }

    //to getting the username
    public String getUserNameFromToken(String token){

        String user = parser().setSigningKey(secreteKey)
                .build()
                .parseSignedClaims(token)
                .getPayload().getSubject().toString();

        return user;

        /*Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
        in this code parseClaimsJws(token) method is decrypted replace with
         .build().parseSignedClaims(token)*/
    }
    //Create a method with a Boolean return type for check token is valid or not
    public Boolean getTokenVailedity(String token){
        try {
            Date expiration = parser()
                    .setSigningKey(secreteKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .getExpiration();
            System.err.println("expiration: "+expiration);

            Date issuedAt = parser()
                    .setSigningKey(secreteKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .getIssuedAt();
            System.out.println("issuedAt: "+issuedAt);
            //it returns every time false if token is availed if token is invalided then it throws exception
            return !expiration.before(issuedAt);

        }catch (ExpiredJwtException e){
            return  false;
        }
    }
    public Boolean isTokenVailed(String requestUserName,String token){
        /*Here we check Two things
        * 1. token time interval= true and
        * 2. Check UserName match or not from token */
        Boolean tokenVailedity = this.getTokenVailedity(token);
        String userNameFromToken = this.getUserNameFromToken(token);
        return userNameFromToken.equals(requestUserName) & tokenVailedity;
    }
}
