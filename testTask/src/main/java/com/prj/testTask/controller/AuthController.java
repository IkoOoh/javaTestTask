package com.prj.testTask.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prj.testTask.dto.ErrorResponse;
import com.prj.testTask.dto.RefreshTokenRequest;
import com.prj.testTask.dto.UserLoginRequest;
import com.prj.testTask.service.KeycloakAuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private KeycloakAuthService keycloakAuthService;

    @PostMapping("/token")
    public ResponseEntity<?> getToken(@RequestBody UserLoginRequest request) {
        Map<String, Object> tokens = keycloakAuthService.getTokens(request.getUsername(), request.getPassword());
        if (tokens != null && tokens.containsKey("access_token")) {
            return new ResponseEntity<>(tokens, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ErrorResponse("UNAUTHORIZED","wrong credentials"),HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@RequestBody RefreshTokenRequest request) {
        Map<String, Object> tokens = keycloakAuthService.refreshToken(request.getRefreshToken());
        if (tokens != null && tokens.containsKey("access_token")) {
            return new ResponseEntity<>(tokens, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ErrorResponse("UNAUTHORIZED","wrong credentials") , HttpStatus.UNAUTHORIZED);
        } 
    }
}
