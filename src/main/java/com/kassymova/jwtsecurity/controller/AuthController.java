package com.kassymova.jwtsecurity.controller;


import com.kassymova.jwtsecurity.request.AuthenticateRequest;
import com.kassymova.jwtsecurity.request.RegisterRequest;
import com.kassymova.jwtsecurity.response.AuthenticationResponse;
import com.kassymova.jwtsecurity.response.ResponseWithMessage;
import com.kassymova.jwtsecurity.response.UserProfile;
import com.kassymova.jwtsecurity.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authService;

    @PostMapping("/register")
    public ResponseEntity<ResponseWithMessage> register(
            @RequestBody RegisterRequest request) {
        return authService.newRegister(request);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody AuthenticateRequest request) {
        return ResponseEntity.ok(authService.authenticate(request));
    }

    @GetMapping("/profile")
    public UserProfile getProfile(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return authService.getUserProfile(userDetails.getUsername());
    }
}
