package com.xische.currencyexchange.controller;

import com.xische.currencyexchange.TestSecurityConfig;
import com.xische.currencyexchange.auth.JwtTokenProvider;
import com.xische.currencyexchange.model.AuthenticationRequest;
import com.xische.currencyexchange.model.TokenResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebFluxTest(AuthController.class)
@Import(TestSecurityConfig.class)
public class AuthControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private JwtTokenProvider tokenProvider;

    @MockBean
    private ReactiveAuthenticationManager authenticationManager;

    private AuthenticationRequest authRequest;
    private TokenResponse tokenResponse;

    @BeforeEach
    public void setUp() {
        authRequest = new AuthenticationRequest("testuser", "password");
        tokenResponse = new TokenResponse("Bearer", "accessToken", 3600L, "refreshToken");
    }

    @Test
    public void testLoginSuccess() {
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(Mono.just(new UsernamePasswordAuthenticationToken("testuser", "password")));
        when(tokenProvider.createTokenResponse(any())).thenReturn(tokenResponse);

        webTestClient.post()
                .uri("/auth/login")
                .bodyValue(authRequest)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().valueEquals(HttpHeaders.AUTHORIZATION, "Bearer accessToken")
                .expectBody(TokenResponse.class)
                .isEqualTo(tokenResponse);
    }
}