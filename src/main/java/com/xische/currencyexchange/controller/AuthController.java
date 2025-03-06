package com.xische.currencyexchange.controller;

import com.xische.currencyexchange.auth.JwtTokenProvider;
import com.xische.currencyexchange.model.AuthenticationRequest;
import com.xische.currencyexchange.model.TokenResponse;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/auth")
@Validated
public class AuthController {

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);
    private final JwtTokenProvider tokenProvider;
    private final ReactiveAuthenticationManager authenticationManager;

    public AuthController(JwtTokenProvider tokenProvider, ReactiveAuthenticationManager authenticationManager) {
        this.tokenProvider = tokenProvider;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    public Mono<ResponseEntity<TokenResponse>> login(
            @Valid @RequestBody Mono<AuthenticationRequest> authRequest) {
        return authRequest
                .flatMap(login -> this.authenticationManager
                        .authenticate(new UsernamePasswordAuthenticationToken(
                                login.username(), login.password()))
                        .map(this.tokenProvider::createTokenResponse))
                .map(tokenResponse -> {
                    HttpHeaders httpHeaders = new HttpHeaders();
                    httpHeaders.add(HttpHeaders.AUTHORIZATION, "Bearer " + tokenResponse.accessToken());
                    return new ResponseEntity<>(tokenResponse, httpHeaders, HttpStatus.OK);
                });
    }
}