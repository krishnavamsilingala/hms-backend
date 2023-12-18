package com.hospital.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {

    @PostMapping("/api/login")
    public ResponseEntity<AuthenticationResponse> generateBasicAuthHeader(@RequestBody AuthenticationRequest request) {
        String username = request.getUsername();
        String password = request.getPassword();

        String authHeader = "Basic " + java.util.Base64.getEncoder().encodeToString((username + ":" + password).getBytes());

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set(HttpHeaders.AUTHORIZATION, authHeader);

        return new ResponseEntity<>(new AuthenticationResponse(authHeader), responseHeaders, HttpStatus.OK);
    }
}
