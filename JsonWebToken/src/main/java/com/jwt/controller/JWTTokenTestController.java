package com.jwt.controller;

import com.jwt.jwtToken.ApplicationTokenGeneretor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/key")
public class JWTTokenTestController {

    @Autowired
    private ApplicationTokenGeneretor tokenGeneretor;

    @GetMapping(path = "/user/{userId}")
    public ResponseEntity<String> getUserToken(@PathVariable String userId) {
        return new ResponseEntity<>(tokenGeneretor.getJwtToken(userId), HttpStatus.OK);
    }

    @GetMapping(path = "/user")
    public ResponseEntity<String> getUserName(@RequestHeader String header) {
            return new ResponseEntity<>(tokenGeneretor.getUserNameFromToken(header), HttpStatus.OK);
    }

    @GetMapping(path = "/getvailedity")
    public ResponseEntity<Boolean> getVailedityOfToken(@RequestHeader String header) {
        System.err.println(header);
        return new ResponseEntity<>(tokenGeneretor.getTokenVailedity(header), HttpStatus.OK);
    }
    @GetMapping(path = "/isTokenVailed/{userName}")
    public ResponseEntity<Boolean> isTokenVailed(@RequestHeader String header ,@PathVariable String userName ){
        System.err.println(header);
        System.err.println(userName);
        return new ResponseEntity<>(this.tokenGeneretor.isTokenVailed(userName,header),HttpStatus.OK);
    }
}
