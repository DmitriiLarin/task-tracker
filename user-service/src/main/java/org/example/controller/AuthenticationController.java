package org.example.controller;


import lombok.RequiredArgsConstructor;
import org.example.dto.request.SignInRequest;
import org.example.dto.request.SignUpRequest;
import org.example.dto.response.AuthenticationResponse;
import org.example.security.AuthenticationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final static String ACCESS_TOKEN = "accessToken";
    private final static String REFRESH_TOKEN = "refreshToken";

    @PostMapping("/signup")
    public AuthenticationResponse signUp(@RequestBody SignUpRequest request) {
        return authenticationService.signUp(request);
    }

    @PostMapping("/signin")
    public AuthenticationResponse signIn(@RequestBody SignInRequest request) {
        return authenticationService.signIn(request);
    }

    @PostMapping("/refresh")
    public AuthenticationResponse refreshAccessToken(@RequestHeader(REFRESH_TOKEN) String refreshToken) {
        return authenticationService.refreshAccessToken(refreshToken);
    }

    @GetMapping("/isauthorized")
    public boolean isAuthorized(@RequestHeader(ACCESS_TOKEN) String accessToken) {
        return authenticationService.isAuthorized(accessToken);
    }

    @GetMapping("/isadmin")
    public boolean isAdmin(@RequestHeader(ACCESS_TOKEN) String accessToken) {
        return authenticationService.isAdmin(accessToken);
    }
}
