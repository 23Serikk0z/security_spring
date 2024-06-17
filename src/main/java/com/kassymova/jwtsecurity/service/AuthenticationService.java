package com.kassymova.jwtsecurity.service;


import com.kassymova.jwtsecurity.entity.Role;
import com.kassymova.jwtsecurity.entity.User;
import com.kassymova.jwtsecurity.repository.UserRepository;
import com.kassymova.jwtsecurity.request.AuthenticateRequest;
import com.kassymova.jwtsecurity.request.RegisterRequest;
import com.kassymova.jwtsecurity.response.AuthenticationResponse;
import com.kassymova.jwtsecurity.response.ResponseWithMessage;
import com.kassymova.jwtsecurity.response.UserProfile;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;


    public Optional<User> checkEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public ResponseEntity<ResponseWithMessage> newRegister(RegisterRequest request){
        try {
            if(checkEmail(request.getEmail()).isPresent()){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseWithMessage("Email already in use", false, null));
            }

            var user = User.builder()
                    .firstName(request.getFirstName())
                    .lastName(request.getLastName())
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .role(Role.USER)
                    .build();
            userRepository.save(user);
            var jwtToken = jwtService.generateToken(user);
            return new ResponseEntity<>(ResponseWithMessage
                    .builder()
                    .message("New user registered")
                    .success(true)
                    .token(jwtToken)
                    .build(), HttpStatus.CREATED);
        }
        catch (Exception e){
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    public AuthenticationResponse register(RegisterRequest request) {
//
//        var user = User.builder()
//                .firstName(request.getFirstName())
//                .lastName(request.getLastName())
//                .email(request.getEmail())
//                .password(passwordEncoder.encode(request.getPassword()))
//                .role(Role.USER)
//                .build();
//        userRepository.save(user);
//        var jwtToken = jwtService.generateToken(user);
//        return AuthenticationResponse
//                .builder()
//                .token(jwtToken)
//                .build();
//    }


    public AuthenticationResponse authenticate(AuthenticateRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow();

        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse
                .builder()
                .token(jwtToken)
                .build();
    }


    public UserProfile getUserProfile(String email){
        var user = userRepository.findUserByEmail(email);
        return new UserProfile(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassword(), user.getRole());
    }
}
