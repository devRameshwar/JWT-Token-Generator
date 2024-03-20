package com.jwt.jwtToken;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component
public class ApplicationTokenGeneretor {

    private String secreteKey = "ADTWEBBISN1NI19LO0JQW1TYBVBVF8HVCX8";

    public String getJwtToken(String UserId) {

        return Jwts.builder()
                .setSubject(UserId)
                .setIssuer("ICICI Bank")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(5)))
                .signWith(SignatureAlgorithm.HS256,secreteKey.getBytes())
                .encodePayload(true)
                .compact();

    }
}
